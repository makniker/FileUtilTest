plugins {
    id("java")
    id("application")
}

group = "mak.niker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jcommander:jcommander:1.83")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("mak.niker.App")
}