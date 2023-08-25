package com.example

class Maven implements Serializable {

    def script

    Maven(script) {
        this.script = script
    }


    def incrementVersion() {
        script.echo('Incrementing version...')
        script.sh('./scripts/incrementVersion.sh')
        script.setVersion()
    }

    def setVersion() {
        script.echo('Setting version...')
        def version = script.sh(
            script: 'grep -oPm1 "(?<=<version>)[^<]+" pom.xml',
            returnStdout: true).trim()

        script.env.versionTag = version
    }

    def pushVersion(){
        script.echo('Push version update to repository...')
        script.withCredentials([script.usernamePassword(credentialsId: 'github-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            script.sh('''
                git config --global user.email 'jenkins@example.com'
                git config --global user.name 'jenkins'
                git remote set-url origin https://$USERNAME:$PASSWORD@github.com/jokerwrld999/java-maven-app.git
                git add . && git commit -m '[ci/cd] Jenkins update version file'
                git push origin HEAD:main
            ''')
        }
    }
}