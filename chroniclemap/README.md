# Manorrock Eagle

## ChronicleMap implementation

If you want to use ChronicleMap as a Key-Value store you will need to do the following.

1. Add the dependency.
2. Create the ChronicleMapKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle.azure</groupId>
        <artifactId>eagle-chroniclemap</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the ChronicleMapBKeyValueStore

The following code snippet will get your started.

```java
    ChronicleMapKeyValueStore store = new ChronicleMapKeyValueStore(
        "name",
        50000
    );
```

Then the rest is using the KeyValueStore APIs.

## Overriding the ChronicleMap dependency

Beneath the covers the ChronicleMap implementation uses ChronicleMap. If your
own project uses ChronicleMap, or any of its dependencies you should be aware of
this. Note the version of the ChronicleMap dependency that this implementation
uses can be found in the pom.xml file of this module.

## Factory properties

If you want to create the ChronicleMapKeyValueStore using the 
KeyValueStoreFactory use the following

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.azure.chroniclemap.ChronicleMapKeyValueStore
| name | the name of the map
| maxSize | the maximum size of the map
