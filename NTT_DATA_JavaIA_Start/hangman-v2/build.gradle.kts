import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.compile.JavaCompile

plugins {
    id("org.openjfx.javafxplugin") version "0.0.14"
    id("java")
    application
}

group = "br.com.phoenix"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "21.0.2"
    modules = listOf("javafx.controls")
}

dependencies {
    implementation("com.googlecode.lanterna:lanterna:3.1.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainClass.set("br.com.phoenix.hangman.fx.HangmanFxApp")
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

tasks.register("runLanterna", JavaExec::class) {
    group = "application"
    description = "Executa a versão TUI (Lanterna) do jogo"
    mainClass.set("br.com.phoenix.hangman.tui.LanternaHangmanApp")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register("runCli", JavaExec::class) {
    group = "application"
    description = "Executa a versão CLI tradicional"
    mainClass.set("br.com.phoenix.hangman.Main")
    classpath = sourceSets["main"].runtimeClasspath
}
