#!/usr/bin/env groovy

import com.ci.Maven

def call() {
    return new Maven(this).versionUpdate()
}