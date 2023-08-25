package com.ci

class Maven implements Serializable {

    def script

    Maven(script) {
        this.script = script
    }

    def incrementVersion() {
        script.echo "Incrementing version..."
        script.sh "mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit"

        def version = script.sh script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
        script.env.versionTag = "$version-$script.BUILD_NUMBER"
    }

    def buildApp(){
        script.echo "Building Application..."
        script.sh "mvn clean package"
    }

    def versionUpdate(){
        script.echo "Push version update to repository..."
        script.withCredentials([script.usernamePassword(credentialsId: 'github-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            script.sh "git config --global user.email 'jenkins@example.com'"
            script.sh "git config --global user.name 'jenkins'"
            script.sh "git remote set-url origin https://$script.USERNAME:$script.PASSWORD@github.com/jokerwrld999/java-maven-app.git"
            script.sh "git add . && git commit -m '[ci/cd] Jenkins update version file'"
            script.sh "git push origin HEAD:main"
        }
    }
}