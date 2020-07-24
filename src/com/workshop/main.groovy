#!/usr/bin/groovy
package com.psl

import com.psl.Config
import com.workshop.stages.*
import com.workshop.Utils

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