#!/usr/bin/groovy
package com.jenkins_psl

import com.jenkins_psl.Config
import com.jenkins_psl.stages.*
import com.jenkins_psl.utils

def main(script) {
    println "Hello blowart"

    //Initialization
    c = new Config()
    u = new utils()

    sprebuild = new prebuild()
    sbuild = new build()
    spostbuild = new postbuild()

    // Pipeline specific variable get from injected env
    // Mandatory variable wil be check at details & validation steps
    def repository_name = ("${script.env.repository_name}" != "null") ? "${script.env.repository_name}" : ""
    def branch_name = ("${script.env.branch_name}" != "null") ? "${script.env.branch_name}" : ""
    def git_user = ("${script.env.git_user}" != "null") ? "${script.env.git_user}" : ""
    def app_port = ("${script.env.app_port}" != "null") ? "${script.env.app_port}" : ""
    def pr_num = ("${script.env.pr_num}" != "null") ? "${script.env.pr_num}" : ""

    // Timeout for Healtcheck
    def timeout_hc = (script.env.timeout_hc != "null") ? script.env.timeout_hc : 10

    // Have default value
    def docker_registry = ("${script.env.docker_registry}" != "null") ? "${script.env.docker_registry}" : "${c.default_docker_registry}"

    // Initialize docker tools
    def dockerTool = tool name: 'docker', type: 'dockerTool'

    p = new Pipeline(
        repository_name,
        branch_name,
        git_user,
        app_port,
        pr_num,
        dockerTool,
        docker_registry,
        timeout_hc
    )

    ansiColor('xterm') {
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
            // withCredentials([usernamePassword(credentialsId: 'dimasmamot-github-personal', passwordVariable: 'git_token', usernameVariable: 'git_username')]) {
                // Check pr merged atau belum dari variable is_merged
                // Kalau sudah langsung deploy
                // Kalau belum check apa merge nya udah berhasil atau ngga
                // Kalau berhasil lanjut deploy
                // Kalau gagal jangan lanjut
            }
            println "Want to merge blowart?"
        }

        // stage('Deploy') {
        //     sdeploy.deploy(p)
        // }

        // stage('Service Healthcheck') {
        //     spostdeploy.healthcheck(p)
        // }
    }
}
return this