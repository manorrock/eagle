# Manorrock Eagle

## Hazelcast implementation

If you want to use Hazelcast as a Key-Value store you will need to do the
following.

1. Add the dependency.
2. Create the HazelcastKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-hazelcast</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the HazelcastKeyValueStore

The following snippet shows you how to create the HazelcastKeyValueStore

```java

    HazelcastKeyValueStore store = new HazelcastKeyValueStore("name");
```

Then the rest is using the KeyValueStore APIs.

## Factory properties

If you want to create the HazelcastKeyValueStore using the KeyValueStoreFactory
use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.hazelcast.HazelcastKeyValueStore
| name | the name of the KeyValueStore
