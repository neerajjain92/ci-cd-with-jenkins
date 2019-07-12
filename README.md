# CI-CD with Jenkins 

This is the playground for setting up CI-CD pipeline with [Jenkins](https://jenkins.io/) and deploy on [Pivotal Web Services](https://console.run.pivotal.io/)

We are leveraging [ngrok - secure introspectable tunnels to localhost](https://ngrok.com/) to publish webhook events running on local jenkins.

Important Pieces for this Pipe-line

1) Jenkins File
2) Manifest File

### Jenkinsfile

```
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                withMaven(maven: 'my_maven_3.6.1') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                                  credentialsId   : 'PIVOTAL_CLOUD_FOUNDRY',
                                  usernameVariable: 'USERNAME',
                                  passwordVariable: 'PASSWORD']]) {

                    sh '/usr/local/bin/cf login -a http://api.run.pivotal.io -u $USERNAME -p $PASSWORD'
                    sh '/usr/local/bin/cf push'
                }
            }
        }
    }
}
```

### Manifest File (To do the deployment in Pivotal WebServices using cloud foundry cli)
```
---
applications:
- name: ci-cd-with-jenkins
  instances: 1
  memory: 800m
  path: target/ci-cd-with-jenkins-0.0.1-SNAPSHOT.jar
```
