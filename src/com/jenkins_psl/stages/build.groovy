#!/usr/bin/groovy
package com.jenkins_psl

import com.jenkins_psl.Pipeline
import com.jenkins_psl.Config

def build(Pipeline p) {
    c = new Config()

    docker.withTool("${c.default_docker_jenkins_tool}") {
        docker.withRegistry("${p.docker_registry}", "${c.default_docker_registry_jenkins_cred}") {
            def image = docker.build("${p.git_user}/${p.repository_name}:build-$BUILD_NUMBER")
            image.push()
            image.push('latest')
        }
    }
}
