def call(Map params) {
    withCredentials([usernamePassword(credentialsId: params.CredId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        // Construct the full image name
        def fullImageBase = "${env.DOCKER_USER}/${params.ImageBase}-${params.ImageSuffix}"

        // Login to the Docker registry
        docker_login(params.Registry, env.DOCKER_USER, env.DOCKER_PASS)

        // Iterate through each tag and push the image separately
        params.Tags.each { tag ->
            echo "Pushing Docker image: ${fullImageBase}:${tag}"
            sh "docker push ${fullImageBase}:${tag}"
        }
    }
}