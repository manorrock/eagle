# Manorrock Eagle

## Filesystem implementation

If you want to use your file system as a Key-Value store then the following 
snippet will get you started.

```java

    File baseDirectory = new File("mybasedir");
    FilesystemKeyValueStore kvs = new FilesystemKeyValueStore(baseDirectory);
```

Then the rest is using the KeyValueStore APIs.
