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
