import org.gradle.jvm.tasks.Jar
plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    implementation("org.snmp4j:snmp4j:2.5.8")
    implementation("org.snmp4j:snmp4j-agent:2.6.1")
    implementation("log4j:log4j:1.2.14")
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
tasks.test {
    useJUnitPlatform()
}


tasks.jar {
    manifest.attributes["Main-Class"] = "com.verdeloro.snmpplugin.SNMPPlugin"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}