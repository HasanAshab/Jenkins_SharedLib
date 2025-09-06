def call(String ImageName, String ImageTag){
  sh "docker push ${ImageName}:${ImageTag}"
}
