# ntfy-java

A [ntfy](https://ntfy.sh) library for Java 17+.

## Installing
ntfy-java is available as `dev.siebrenvde:ntfy-java` at `repo.siebrenvde.dev/releases/`.  
Snapshots are available at `repo.siebrenvde.dev/snapshots/`.

### Gradle
```kotlin
repositories {
    mavenCentral()
    maven("https://repo.siebrenvde.dev/releases/")
}

dependencies {
    implementation("dev.siebrenvde:ntfy-java:0.2.0")
}
```

### Maven
```xml
<repositories>
  <repository>
    <id>siebrenvde-releases</id>
    <url>https://repo.siebrenvde.dev/releases/</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>dev.siebrenvde</groupId>
    <artifactId>ntfy-java</artifactId>
    <version>0.2.0</version>
  </dependency>
</dependencies>
```

## Using
Getting a topic:
```java
// Using the default host (ntfy.sh)
Topic topic = Topic.topic("topic-name").build();

// Using a custom host
Topic topic = Topic.topic("topic-name")
    .host("https://ntfy.example.com")
    .build();
```

Publishing a simple message:
```java
topic.publish("Hello, ntfy!");
```

Messages are created using a builder:
```java
Message message = Message.message()
    .title("Hello, ntfy!")
    .body("This message was sent using ntfy-java.")
    .tags("coffee")
    .actions(Action.view("Open GitHub", "https://github.com/Siebrenvde/ntfy-java"))
    .build();
```

## Building
Building ntfy-java requires at least Java 17.  
You can build it by running `./gradlew build`.  
The build jar will be in `build/libs`.
