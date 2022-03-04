podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      containers:
      - name: gradle
        image: gradle:6.3-jdk14
        command:
        - sleep
        args:
        - 30d
      - name: kaniko
        image: gcr.io/kaniko-project/executor:debug
        command:
        - sleep
        args:
        - 9999999
        volumeMounts:
        - name: kaniko-secret
          mountPath: /kaniko/.docker
      restartPolicy: Never
      volumes:
      - name: kaniko-secret
        secret:
            secretName: dockercred
            items:
            - key: .dockerconfigjson
              path: config.json
''')
// podTemplate(containers: [
//     containerTemplate(
//         name: 'gradle',
//         image: 'gradle:6.3-jdk14',
//         command: 'sleep',
//         args: '30d'
//         ),
// ])
   {
    node(POD_LABEL) {
        stage('Run pipeline against a gradle project') {
           git branch: 'main', url: 'https://github.com/sportstube28/week6.git'
            container('gradle') {
                stage('Set Variables') {
                  if ( env.BRANCH_NAME == "main") {
                    env.image_name = "calculator"
                    env.version = "1.0"
                  }
                  if ( env.BRANCH_NAME == "feature") {
                    env.image_name = "calculator-feature"
                    env.version = "0.1"
                  }
                }
                stage('Build a gradle project') {
                  if ( env.BRANCH_NAME == "palyground") {
                    sh '''
                    chmod +x gradlew
                    ./gradlew -x test
                    '''
                    }
                    else
                    {
                      sh '''
                      chmod +x gradlew
                      ./gradlew
                      '''
                    }
                  }
        stage("jacoco checkstyle") {
          echo "My jacoco checkstyle branch is: ${env.BRANCH_NAME} branch"
          if ( env.BRANCH_NAME != "feature")
           try {
                   sh '''
                   pwd
                   ./gradlew jacocoTestCoverageVerification
                   ./gradlew checkstyleMain
                   '''
            } catch (Exception E) {
                echo 'Failure detected'
            }
            publishHTML (target: [
              reportDir: 'build/reports/jacoco/test/html',
              reportFiles: 'index.html',
              reportName: "JaCoCocheckstyle Report"
            ])
        }
     }
   }
  }
}
    stage('Build Java Image') {
      container('kaniko') {
        stage('Build a Go project') {
          sh '''
            /kaniko/executor --context `pwd` --destination ${env.image_name}:${env.version}
          '''
        }
      }
    }
  }
}
