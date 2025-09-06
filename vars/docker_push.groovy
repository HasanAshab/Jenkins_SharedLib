def call(String ImageName, List Tags) {
    // Iterate through each tag and push the image separately
    Tags.each { tag ->
        echo "Pushing Docker image: ${ImageName}:${tag}"
        sh "docker push ${ImageName}:${tag}"
    }
}