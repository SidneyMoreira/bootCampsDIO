plugins {
    id("java")
    application
}

group = "bt.com.phoenix"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("br.com.phoenix.hangman.Main")
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}