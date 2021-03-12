# Manorrock Eagle

## Oraclhe Coherence implementation

If you want to use (Oracle) Coherence as a Key-Value store you will need to do
the following.

1. Add the dependency.
2. Create the CoherenceMapKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle.azure</groupId>
        <artifactId>eagle-coherence</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the CoherenceMapKeyValueStore

The following code snippet will get your started.

```java
    CoherenceMapKeyValueStore store = new CoherenceMapKeyValueStore("name");
```

Then the rest is using the KeyValueStore APIs.

## Overriding the Coherence dependency

Beneath the covers the Coherence implementation uses Coherence. If your
own project uses Coherence, or any of its dependencies you should be aware of
this. Note the version of the Coherence dependency that this implementation
uses can be found in the pom.xml file of this module.

## Factory properties

If you want to create the CoherenceMapKeyValueStore using the 
KeyValueStoreFactory use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.coherence.CoherenceMapKeyValueStore
| name | the name of the underlying cache
