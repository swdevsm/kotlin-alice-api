plugins {
  alias(libs.plugins.kotlin.jvm)
  `java-library`
  id("com.vanniktech.maven.publish") version "0.35.0"
  signing
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.assertj)

  api(libs.jackson.core)
  api(libs.jackson.databind)
  api(libs.jackson.kotlin)
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

group = getEnvOrProperty("GROUP", "library.group") ?: ""
version = getEnvOrProperty("LIBRARY_VERSION", "library.version") ?: ""

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
//  withJavadocJar()
//  withSourcesJar()
}

kotlin {
  jvmToolchain(17)
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

mavenPublishing {
  coordinates(project.group.toString(), "${project.rootProject.name}-${project.name}", project.version.toString())
  publishToMavenCentral()
  signAllPublications()
  pom {
    name.set("${project.rootProject.name}-${project.name}")
    description.set("Kotlin Library with DTOs for Yandex Alice dialogs API (${project.name})")
    url.set("https://github.com/swdevsm/kotlin-alice-api")
    issueManagement {
      url.set("https://github.com/swdevsm/kotlin-alice-api/issues")
    }

    licenses {
      license {
        name.set("The Apache Software License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("repo")
      }
    }

    developers {
      developer {
        id.set("swdevsm")
        name.set("swdevsm")
        email.set("13076163+swdevsm@users.noreply.github.com")
      }
    }

    scm {
      url.set("https://github.com/swdevsm/kotlin-alice-api")
      connection.set("scm:git://github.com/swdevsm/kotlin-alice-api.git")
      developerConnection.set("scm:git://github.com/swdevsm/kotlin-alice-api.git")
    }
  }
}
