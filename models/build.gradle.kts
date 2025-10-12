plugins {
  alias(libs.plugins.kotlin.jvm)
  `java-library`
  alias(libs.plugins.publisher)
}

dependencies {
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.assertj)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlinPublications {
  publication {
    publicationName.set("-models")
    description.set("Models artifact")
  }
}
