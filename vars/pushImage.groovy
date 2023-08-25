#!/usr/bin/env groovy

import com.ci.Docker

def call(String imageName) {
    return new Docker(this).pushImage(imageName)
}