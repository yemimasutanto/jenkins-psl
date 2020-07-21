#!/usr/bin/groovy
package com.psl

import com.psl.Config

def main(script) {
    // Object initialization
    c = new Config()
    u = new utils()
    sprebuild = new prebuild()

    // Pipeline specific variable get from injected env
    // Mandatory variable will be check at details & validation steps
    def repository_name = ("${script.env.repository_name}" != "null") ? "${script.env.repository_name}" : ""
    def branch_name = ("${script.env.branch_name}" != "null") ? "${script.env.branch_name}" : ""
    def git_user = ("${script.env.git_user}" != "null") ? "${script.env.git_user}" : ""
    def app_port = ("${script.env.app_port}" != "null") ? "${script.env.app_port}" : ""
    def pr_num = ("${script.env.pr_num}" != "null") ? "${script.env.pr_num}" : ""

    //Initialize docker tool
    def dockerTool = tool name: 'docker', type: 'dockerTool'
    
    // Pipeline object
    p = new Pipeline(
        repository_name,
        branch_name,
        git_user,
        app_port,
        pr_num,
        dockerTool
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

        // stage('Build & Push Image') {
        //     // TODO: Call build & push image function
        // }

        // stage('Merge') {
        //     // TODO: Call merge function
        // }

        // stage('Deploy') {
        //     // TODO: Call deploy function
        // }

        // stage('Service Healthcheck') {
        //     // TODO: Call healthcheck function
        // }
    }
}
return this