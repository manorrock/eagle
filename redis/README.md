# Manorrock Eagle

## Redis implementation

If you want to use Redis as a Key-Value store you will need to do the following.

1. Add the Lettuce dependency.
2. Create the RedisKeyValueStore.

### Add the Lettuce dependency

As we do not want to interfere with any dependencies your project might rely on
we have marked our Lettuce dependency as `optional` which means that if you want
to use the Redis KeyValueStore you will need to add the Lettuce dependency to
your project. Replace `PICK_YOUR_VERSION` with the version you want to use (for
the version we tested with see the project pom.xml).

```xml
<dependency>
    <groupId>io.lettuce</groupId>
    <artifactId>lettuce-core</artifactId>
    <version>PICK_YOUR_VERSION</version>
</dependency>
```

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
