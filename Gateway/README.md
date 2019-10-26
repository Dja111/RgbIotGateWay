# Java Repository for RGB - visualizing your surroundings

Hallo!
Diese Branch wird ausschlie�lich f�r die Entwicklung mit der Programmiersprache Java verwendet. Die Branch f�r den Embedded Teil ist unter "Repository" hier in BitBucket zu finden. 

ACHTUNG: 
- Falls du dieses Projekt bereits erfolgreich zum laufen gebracht hast, sind folgende Schritte f�r dich nicht erforderlich.
- # Attention
  You can start the java programm with GUI through the argument "debugMode"
          - in Gradle => run -PappArgs="['debugMode=true']"
          - toogle to false if you want to start it without the GUI
### Installation
Die Installation und Verwendung des Projekts bezogen auf Java folgt in folgenden Schritten:
1. Installiere [IntelliJ](https://www.jetbrains.com/idea/).
2. Erstelle ein neues Projekt unter Verwendung von Gradle
3. GroupID kann frei Ausgew�hlt werden, wobei ArtifactID dem Projektnamen entsprechen soll.
4. Klicke dich durch bist du ein Finish Button siehst. Gerne kannst du auf dem Weg dorthin dein Projekt Pfad �ndern.
5. Soweit so gut ? Nun kommen die Dependencies. Unter deinem Projekt wirst du ein File namens build.gradle sehen. L�sch alles was sich dadrinne befindet und kopiere/f�ge den folgenden Inhalt: 

```sh
plugins {
    id 'java'
}

version '1.0-SNAPSHOT'

sourceCompatibility = 1.8

repositories {
    mavenCentral()

    maven{
        url="${artifactory_contextUrl}/libs-release"
        credentials{
            username = "${artifactory_user}"
            password ="${artifactory_password}"
        }
    }
    maven{
        url="http://ftp1.digi.com/support/m-repo"
    }
}

configurations.all {
    exclude group: 'com.digi', module: 'android-sdk'
    exclude group: 'com.digi', module: 'android-sdk-addon'
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
    compile (group: 'de.ude.es', name: 'gatewaymessagequeue', version: '1.2.2')
    compile group: 'com.digi.xbee', name: 'xbee-java-library', version:'1.2.0'
}
```
6. Erstelle eine Datei namens "gradle.properties" in den selben Verzeichnis wie die von gradle.build. 
7. Logge dich unter [Artifact](http://artifactory.es.uni-due.de:8081) ein(�bliche Unikennung+Passwort), klicke auf dein Namen oben rechts und tippe dein Passwort, dass auf der Website erschienen ist,ein. Generiere dir eine "API Key" und trage diese Ziffer unter artifactory_password ein. artifactory_user ist gleich deine Unikennung. Context URL bleibt so.:  

```sh
artifactory_contextUrl=http://artifactory.es.uni-due.de:8081/artifactory
artifactory_user=unikennung
artifactory_password=AKCp5ccRsqCfmRHqsKNjHUVfZ3BRn3PtYnqvAwcQrbkd9jXxBwfAkATYFDy64TaRW1V17G4nM
```

8.Gradle wird im Hintergrund arbeiten, sobald du die popups in intelliJ akzeptierst. Akzeptiere es, falls Sie nciht automatisch akzeptiert wurden.
9.Wir sind fast fertig. Unter der [Gateway Mesage Queue](https://confluence.es.uni-due.de/display/IM/Gateway+Message+Queue) kannst du Beispielcodes verwenden um auszutesten ob alles funktioniert. 
10. In Intellij, gehe auf "File" => "Projet Structure" => "Libraries" => "+" und w�hle den Ordner LoggerLibrary ein. Best�tige die "OK"�s. Diese Library ist notwendig damit der Code sauber funktioniert. 

Viel Spa� mit dem Projekt.