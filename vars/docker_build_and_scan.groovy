def call(Map params) {
    withCredentials([usernamePassword(credentialsId: params.CredId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        // Construct the full image name
        def fullImageBase = "${env.DOCKER_USER}/${params.ImageBase}-${params.ImageSuffix}"

        // Build the Docker image.
        docker_build(fullImageBase, params.Tags, params.ImageSuffix)

        // Scan the built image with Trivy.
        Trivy needs the full image name and the tag.
        trivy_scan_docker(
            Image: fullImageBase,
            Tag: params.Tags[0],
            Severity: ['HIGH', 'CRITICAL'],
            Scanners: ['os', 'library'],
        )
    }
}