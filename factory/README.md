# Manorrock Eagle

## Factory functionality

If you want to use a factory to create your KeyValueStore you can use the following:

1. Add the dependencies.
2. Create your KeyValueStore using the KeyValueStoreFactory.

### Add the dependencies

To start add the following dependencies.

```xml
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-factory</artifactId>
        <version>MY_VERSION</version>
    </dependency>
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-IMPL</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced with the version you want to use and IMPL should be replaced with the respective implementation you want to use.

### Create your KeyValueStore using the KeyValueStoreFactory

The factory makes it possible to create a KeyValueStore using factory with a map that defines the key/values to be used to create the KeyValueStore.

For example see the snippet below to create a FilesystemKeyValueStore.

```java
    HashMap map = new HashMap();
    map.put("className", "com.manorrock.eagle.filesystem.FilesystemKeyValueStore");
    map.put("baseDirectory", "mydirectory");
    KeyValueStore store = KeyValueStoreFactory.getKeyValueStore(map);
```

Note the key/values in the map are dependent on the underlying KeyValueStore you are trying to create.

See the respective implementation for the correct key/values.

Then the rest is using the KeyValueStore APIs.
