package com.example

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def dockerLogin() {
        script.echo("Login to GHCR...")
        script.withCredentials([script.usernamePassword(credentialsId: 'github_registry-creds', usernameVariable: 'GHCR_USERNAME', passwordVariable: 'GHCR_PASSWORD')]) {
            script.sh('''
                echo $GHCR_PASSWORD | docker login ghcr.io -u $GHCR_USERNAME --password-stdin
            ''')
        }
    }

    def dockerBuild(String imageName) {
        script.echo("Building Docker image...")
        script.sh("""
            docker build -t $imageName .
        """)
    }

    def dockerPush(String imageName) {
        script.echo("Pushing Docker image...")
        script.sh("""
            docker push $imageName
        """)
    }
}
