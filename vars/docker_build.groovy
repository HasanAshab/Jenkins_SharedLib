// Define function
def call(String ImageName, String ImageTag, String ContextPath = '.') {
  sh "docker build -t ${ImageName}:${ImageTag} ${ContextPath}"
}
