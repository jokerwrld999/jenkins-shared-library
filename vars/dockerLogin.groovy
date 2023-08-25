#!/usr/bin/env groovy

import com.ci.Docker

def call() {
    return new Docker(this).dockerLogin()
}