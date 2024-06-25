task customFatJar(type: Jar) {
    manifest {
        attributes 'Main-Class': 'com.verdeloro.snmpplugin.SNMPPlugin'
    }
    archiveBaseName = 'all-in-one-jar'
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }
    with jar
}