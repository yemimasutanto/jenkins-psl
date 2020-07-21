#!/usr/bin/groovy
package com.psl

class Config {
    // TODO: Add Default Variable
    def default_docker_jenkins_tool = 'docker'
    def default_docker_registry = "https://registry-1.docker.io/v2/"
    def default_docker_registry_jenkins_cred = 'docker-credentials'

    // Golang related
    def default_golang_base_image = "tobapramudia/tkpd-demo:onbuild"
}