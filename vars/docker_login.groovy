def call(String Registry, String Username, String Password) {
  sh("docker login -u $Username -p $Password $Registry")
}
