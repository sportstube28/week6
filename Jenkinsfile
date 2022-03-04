podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      containers:
      - name: maven
        image: maven:3.8.1-jdk-8
        command:
        - sleep
        args:
        - 99d
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
''') {
podTemplate(containers: [
    containerTemplate(
        name: 'gradle',
        image: 'gradle:6.3-jdk14',
        command: 'sleep',
        args: '30d'
        ),
])   {

    node(POD_LABEL) {
        stage('Run pipeline against a gradle project') {
           git branch: 'main', url: 'https://github.com/sportstube28/week6.git'
            container('gradle') {
                stage('Build a gradle project') {
                    sh '''
                    chmod +x gradlew
                    ./gradlew test
                    '''
                    }
        stage("jacoco checkstyle") {
          echo "My jacoco checkstyle branch is: ${env.BRANCH_NAME} branch"
          if ( env.BRANCH_NAME == "feature")
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
            /kaniko/executor --context `pwd` --destination bibinwilson/hello-kaniko:1.0
          '''
        }
      }
    }

  }
}
