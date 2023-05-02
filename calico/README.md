# Manorrock Eagle

## Manorrock Calico implementation

If you want to use Manorrock Calico as a Key-Value store you will need to do the
following.

1. Add the dependency.
2. Create the CalicoKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-calico</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the CalicoKeyValueStore

The following snippet shows you how to create the CalicoKeyValueStore

```java

    CalicoKeyValueStore<String, byte[]> store = new CalicoKeyValueStore<>(
                URI.create("http://localhost:8080"));
```

Then the rest is using the KeyValueStore APIs.

## Factory properties

If you want to create the CalicoKeyValueStore using the KeyValueStoreFactory
use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.calico.CalicoKeyValueStore
| uri | the location of Manorrock Calico
