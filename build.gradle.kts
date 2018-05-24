import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.noarg.gradle.NoArgExtension
import org.gradle.jvm.tasks.Jar
import java.time.LocalDateTime

val scalaVersion: String by project.extra
val kotlinLoggingVersion: String by project.extra

plugins {
    val kotlinVersion = "1.2.41"
    val gradleShadowPluginVersion = "2.0.4"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion // apply false
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion apply false
    id("com.github.johnrengelman.shadow") version gradleShadowPluginVersion apply false
}

subprojects {

    apply {
        plugin("scala")
        plugin("application")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("com.github.johnrengelman.shadow")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        // kotlin
        compile(kotlin("stdlib-jdk8"))
        compile(kotlin("reflect"))
    }

    configure<NoArgExtension> {
        annotation("com.sumo.experiments.NoArgy")
    }

    tasks {
        withType<KotlinCompile>().all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
                freeCompilerArgs = listOf("-Xjsr305=strict")
                javaParameters = true
            }
        }

        withType<ShadowJar> {
            setZip64(true)
            dependencies {
                include(dependency(":shared"))
                include(dependency("org.jetbrains.kotlin::.*"))
            }
        }

        withType<Jar> {
            manifest {
                attributes(mapOf(
                    "Implementation-Version" to version,
                    "Build-Time" to LocalDateTime.now().toString() // Date().format("yyyy-MM-dd'T'HH:mm:ssZ")
                ))
            }
        }
    }

}
