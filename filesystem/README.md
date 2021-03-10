# Manorrock Eagle

## Filesystem implementation

If you want to use the filesystem as a Key-Value store you will need to do the following.

1. Add the dependency.
2. Create the FilesystemKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-filesystem</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the FilesystemKeyValueStore

The following snippet shows you how to create the FilesystemKeyValueStore

```java

    File baseDirectory = new File("mybasedir");
    FilesystemKeyValueStore store = new FilesystemKeyValueStore(baseDirectory);
```

Then the rest is using the KeyValueStore APIs.

## Factory properties

If you want to create the FilesystemKeyValueStore using the KeyValueStoreFactory use the following

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.filesystem.FilesystemKeyValueStore
| baseDirectory | the base directory
