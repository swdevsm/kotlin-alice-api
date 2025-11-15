plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.publisher)
}

allprojects {
  group = "ru.swdevsm"
  version = "0.0.1"

  repositories {
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "org.jetbrains.kotlin.jvm")

  java {
    toolchain {
      languageVersion = JavaLanguageVersion.of(17)
    }
  }

  tasks.test {
    useJUnitPlatform()
  }

  tasks.jar {
    manifest {
      attributes(
        mapOf(
          "Implementation-Title" to "Kotlin Library with DTOs for Yandex Alice dialogs API",
          "Implementation-Version" to project.version,
        ),
      )
    }
  }
}

kotlinPublications {
  defaultGroup.set("ru.swdevsm")
  defaultArtifactIdPrefix.set("kotlin-alice-api")
  // Set to false if you want to publish empty Javadoc JARs. Maven Central is OK with it
  fairDokkaJars.set(false)

  //todo: add signingCredentials/sonatypeSettings/pom if need it

  localRepositories {
    defaultLocalMavenRepository()
  }
}
