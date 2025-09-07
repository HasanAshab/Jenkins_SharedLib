def call(Map params) {
    // Set default values for optional parameters
    def severities = params.Severities ?: ['HIGH', 'CRITICAL']
    def vulnTypes = params.VulnTypes ?: ['os', 'library']
    
    // Ensure required parameters are provided
    if (!params.image || !params.tag) {
        error("Trivy scan parameters 'image' and 'tag' are required.")
    }
    
    def fullImageName = "${params.Image}:${params.Tag}"
    echo "Starting Trivy scan for image: ${fullImageName}"
    
    // Convert lists to comma-separated strings for the Trivy command
    def severityString = severities.join(',')
    def vulnTypeString = vulnTypes.join(',')

    sh """
        trivy image \\
            --severity ${severityString} \\
            --vuln-type ${vulnTypeString} \\
            --exit-code 1 \\
            --format table \\
            ${fullImageName}
    """
}