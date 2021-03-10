# Manorrock Eagle

## Path implementation

If you want to use the Path API as a Key-Value store you will need to do the 
following.

1. Add the dependency.
2. Create the PathKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-path</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the PathKeyValueStore

The following snippet shows you how to create the PathKeyValueStore

```java

    Path basePath = new File("mybasedir").toPath();
    PathKeyValueStore store = new PathKeyValueStore(basePath);
```

Then the rest is using the KeyValueStore APIs.
