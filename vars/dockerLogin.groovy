#!/usr/bin/env groovy

import com.docker-mvn.Docker

def call() {
    return new Docker(this).dockerLogin()
}