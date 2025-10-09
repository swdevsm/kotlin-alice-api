import org.jetbrains.kotlinx.publisher.apache2
import org.jetbrains.kotlinx.publisher.developer
import org.jetbrains.kotlinx.publisher.githubRepo

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

  configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
    jvmToolchain(17)
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  tasks.withType<Jar> {
    manifest {
      attributes["Implementation-Title"] = "Kotlin Library with DTOs for Yandex Alice dialogs API"
      attributes["Implementation-Version"] = "0.0.1"
    }
  }
}

kotlinPublications {
  // Default group for all publications
  defaultGroup.set("ru.swdevsm")

  // Prefix for artifacts for all publications
  defaultArtifactIdPrefix.set("kotlin-alice-api")

  // Set to false if you want to publish empty Javadoc JARs. Maven Central is OK with it
  fairDokkaJars.set(false)

  // Signing credentials. You will not be able to publish an artifact to Maven Central without signing
//  signingCredentials(
//    "key.id",
//    "key.private",
//    "key.passphrase",
//  )
//
//  sonatypeSettings(
//      "some user",
//      "some password",
//      // Description that will be shown on your Sonatype admin page
//      "repository description",
//  )

  pom {
    // Use this convenience extension to set up all needed URLs
    // for the POM in case you're using GitHub
    githubRepo("github_user", "github_repo")

    // The year your library was first time published to a public repository
    inceptionYear.set("2025")

    licenses {
      apache2()
    }

    developers {
      developer("nickname", "name", "email")
    }
  }

  localRepositories {
    // Default location for the local repository is build/artifacts/maven/
    defaultLocalMavenRepository()
  }
}
