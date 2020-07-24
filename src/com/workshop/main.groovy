#!/usr/bin/groovy
package com.workshop

import com.workshop.Config
import com.workshop.stages.*
import com.workshop.utils

def main(script) {
    // Object initialization

    // Pipeline object

    ansiColor('xterm') {
        stage('Test stage') {
            println "Hello!"
        }
    }
}
return this
