#!/usr/bin/env groovy

import com.docker-mvn.Docker

def call(String imageName) {
    return new Docker(this).pushImage(imageName)
}