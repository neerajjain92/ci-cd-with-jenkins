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

### Plugin used inside Jenkins

 1) [Pipeline Maven Plugin](https://wiki.jenkins.io/display/JENKINS/Pipeline+Maven+Plugin)
 2) [Embeddable Build Status Plugin](https://wiki.jenkins.io/display/JENKINS/Embeddable+Build+Status+Plugin)


# BLUE GREEN Deployment on Cloud Foundry
---

- In order to do blue-green deployment, create 2 artifacts one with `ci-cd-blue` and another `ci-cd-green`

Following Steps will deploy Blue/Green war file to Cloud Foundry

```
1) Login to Cloud Foundry ==> cf login
2) Check which apps are running ==> cf apps
3) Deploy app in cloud foundry ==> cf push -f manifest-blue.yml/manifest-green.yml
``` 

#### Let's map a generic route https://ci-cd-production.cfapps.io/version to these blue and green 

```
 cf map-route ci-cd-green cfapps.io --hostname ci-cd-production
 cf map-route ci-cd-blue cfapps.io --hostname ci-cd-production
```

- Now both blue and green instance are being pointed out by `ci-cd-production` and since Cloud Foundry 
GO Router implements Software Load balancer and uses RoundRobin out of the box to distribute load.
So we see output from blue and green interchangeably

Now since our Green features are good to go in production for all set of users, let's just remove blue 
mapping altogether from ci-cd-production route.

```
cf unmap-route ci-cd-blue cfapps.io --hostname ci-cd-production
``` 

Now production only points to green instance, We have successfully deployed new App version with No downtime required,
using Blue-Green Deployment strategy.
