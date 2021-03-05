# Manorrock Eagle

## Filesystem implementation

If you want to use the filesystem as a Key-Value store you will need to do the following.

1. Add the dependency.
2. Create the FilesystemKeyValueStore.

### Add the depedency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-filesystem</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.


If you want to use your file system as a Key-Value store then the following 
snippet will get you started.

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
