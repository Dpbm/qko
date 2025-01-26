plugins {
    kotlin("jvm") version "2.0.21"
    `java-library`
    `maven-publish`
    signing
}

group = "org.quantum"
version = "1.0"

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Dpbm/qko")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }

        /*maven{
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }*/
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "qko"
            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name = "QKO"
                description = "A open source library for simulating your quantum circuits with Kotlin."
                url = "https://github.com/Dpbm/qko"
              
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                
                developers {
                    developer {
                        id = "Dpbm"
                        name = "Alexandre Silva"
                        email = "dpbm136@gmail.com"
                    }
                }
                
                scm {
                    connection = "scm:git:git://github.com/Dpbm/qko.git"
                    developerConnection = "scm:git:ssh://github.com/Dpbm/qko.git"
                    url = "http://github.com/Dpbm/qko/"
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}