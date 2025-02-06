plugins {
    id("java")
    kotlin("jvm")
}

group = "mak.niker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jcommander:jcommander:1.83")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}