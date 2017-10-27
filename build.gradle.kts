import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.noarg.gradle.NoArgExtension
import org.gradle.jvm.tasks.Jar
import java.time.LocalDateTime

val scalaVersion by project

plugins {
    val kotlinVersion = "1.1.51"
    val gradleShadowPluginVersion = "2.0.1"
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
        compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
        compile("org.jetbrains.kotlin:kotlin-reflect")
    }

    configure<NoArgExtension> {
        annotation("com.sumo.experiments.NoArgy")
    }

    tasks {
        withType<KotlinCompile>().all {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xjsr305=strict")
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
