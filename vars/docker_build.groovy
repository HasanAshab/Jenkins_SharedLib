def call(String ImageName, List Tags, String ContextPath = '.') {
    // Construct the list of '-t' arguments from the provided tags
    // The `collect` method transforms the list of tags into a list of full tag arguments.
    // The `join` method then combines them into a single space-separated string.
    def tagsString = Tags.collect { "-t ${ImageName}:${it}" }.join(' ')

    // Build the Docker image with all the tags in a single command
    sh "docker build ${tagsString} ${ContextPath}"
}