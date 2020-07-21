#!/usr/bin/groovy
package com.psl

import com.psl.Config
import com.psl.stages.*
import com.psl.Utils

def main(script) {
    // Object initialization
    c = new Config()
    u = new Utils()
    sprebuild = new prebuild()
    sbuild = new build()
    spostbuild = new postbuild()
    sdeploy = new deploy()
    spostdeploy = new postdeploy()

    // Pipeline specific variable get from injected env
    // Mandatory variable will be check at details & validation steps
    def repository_name = ("${script.env.repository_name}" != "null") ? "${script.env.repository_name}" : ""
    def branch_name = ("${script.env.branch_name}" != "null") ? "${script.env.branch_name}" : ""
    def git_user = ("${script.env.git_user}" != "null") ? "${script.env.git_user}" : ""
    def app_port = ("${script.env.app_port}" != "null") ? "${script.env.app_port}" : ""
    def pr_num = ("${script.env.pr_num}" != "null") ? "${script.env.pr_num}" : ""

    // Have default value
    def docker_registry = ("${script.env.docker_registry}" != "null") ? "${script.env.docker_registry}" : "${c.default_docker_registry}"

    //Initialize docker tool
    def dockerTool = tool name: 'docker', type: 'dockerTool'

    // Pipeline object
    p = new Pipeline(
        repository_name,
        branch_name,
        git_user,
        app_port,
        pr_num,
        dockerTool,
        docker_registry
    )

    ansiColor('xterm') {
        // stage('Test') {
        //     println "Hello gay!"
        // }
        stage('Pre Build - Details') {
            sprebuild.validation(p)
            sprebuild.details(p)
        }

        stage('Pre Build - Checkout & Test') {
            sprebuild.checkoutBuildTest(p)
        }

        stage('Build & Push Image') {
            sbuild.build(p)
        }

        stage('Merge') {
            spostbuild.merge(p)
        }

        stage('Deploy') {
            sdeploy.deploy(p)
        }

        stage('Service Healthcheck') {
            spostdeploy.healthcheck(p)
        }
    }
}
return this