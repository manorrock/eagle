# Manorrock Eagle

## Redis implementation

If you want to use Redis as a Key-Value store then the following snippet will
get you started.

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
