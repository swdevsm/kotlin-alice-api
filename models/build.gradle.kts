plugins {
  alias(libs.plugins.kotlin.jvm)
  `java-library`
  alias(libs.plugins.publisher)
}

dependencies {
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.assertj)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  // This dependency is exported to consumers, that is to say found on their compile classpath.
  api(libs.commons.math3)

  // This dependency is used internally, and not exposed to consumers on their own compile classpath.
  implementation(libs.guava)
}

kotlinPublications {
  publication {
    publicationName.set("-models")
    description.set("Models artifact")
  }
}
