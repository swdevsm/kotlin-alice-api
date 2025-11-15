plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.publisher)
}

fun getEnv(varName: String): String? = System.getenv(varName)
fun getProperty(propName: String): String? = project.findProperty(propName) as? String
fun getEnvOrProperty(envVar: String, propName: String = envVar, defaultValue: String? = null): String? {
  return getEnv(envVar) ?: getProperty(propName) ?: defaultValue
}

fun requireEnv(envVar: String, propName: String = envVar): String {
  return getEnvOrProperty(envVar, propName)
    ?: throw GradleException("Required environment variable or property '$envVar' not found")
}

allprojects {
  group = "ru.swdevsm"
  version = getEnvOrProperty("LIBRARY_VERSION", "library.version") ?: "0.0.1-SNAPSHOT"

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

  signingCredentials(
    key = requireEnv("SIGNING_KEY_ID", "key.id"),
    privateKey = requireEnv("SIGNING_KEY_PRIVATE", "key.private"),
    keyPassphrase = requireEnv("SIGNING_KEY_PASSPHRASE", "key.passphrase"),
  )

  //todo: add signingCredentials/sonatypeSettings/pom if need it

  localRepositories {
    defaultLocalMavenRepository()
  }
}
