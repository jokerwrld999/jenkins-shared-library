#!/usr/bin/env groovy

import com.docker-mvn.Maven

def call() {
    return new Maven(this).incrementVersion()
}