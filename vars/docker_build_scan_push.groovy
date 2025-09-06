def call(Map params) {
    withCredentials([usernamePassword(credentialsId: params.CredId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        // Construct the full image name
        def fullImageBase = "${env.DOCKER_USER}/${params.ImageBase}-${params.ImageSuffix}"

        // Log in to the Docker registry
        docker_login(params.Registry, env.DOCKER_USER, env.DOCKER_PASS)

        // Build the Docker image.
        docker_build(fullImageBase, params.Tags, params.ImageSuffix)

        // Scan the built image with Trivy.
        // Trivy needs the full image name and the tag.
        trivy(
            imageName: "${fullImageBase}:${params.Tags[0]}",
            severity: 'HIGH,CRITICAL',
            scanners: 'os,library',
        )

        if (params.Push) {
          // Push the Docker image(s).
          docker_push(fullImageBase, params.Tags)
        }
    }
}