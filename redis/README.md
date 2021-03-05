# Manorrock Eagle

## Redis implementation

If you want to use Redis as a Key-Value store you will need to do the following.

1. Add the dependency.
2. Create the RedisKeyValueStore.

### Add the depedency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle</groupId>
        <artifactId>eagle-redis</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the RedisKeyValueStore

The following code snippet will get your started.

```java
    URI uri = RedisURI.Builder
        .redis("localhost")
        .withPort(4312)
        .withSsl(false)
        .withPassword("mypassword")
        .build()
        .toURI();

    RedisKeyValueStore kvs = new RedisKeyValueStore(uri);
```

Then the rest is using the KeyValueStore APIs.

### Overriding the Lettuce dependency

Beneath the covers the Redis implementation uses Lettuce. If your own project
uses Lettuce you should be aware of this. Note the version of the Lettuce dependency
that this implementation use can be found in the pom.xml file.
