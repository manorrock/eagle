# Manorrock Eagle

This project delivers you with a Key-Value Store abstraction.

Our current list of features/implementations available:

1. [Azure Cosmos DB implementation](azure/cosmosdb/README.md)
1. [Azure Blob Storage implementation](azure/blob/README.md)
1. [ChronicleMap implementation](chroniclemap/README.md)
1. [Factory functionality](factory/README.md)
1. [Filesystem implementation](filesystem/README.md)
1. [Path implementation](path/README.md)
1. [Redis implementation](redis/README.md)

Our list of things under consideration:

1. Azure Files implementation
1. CDI integration
1. Hazelcast implementation
1. JDBC implementation
1. Oracle Coherence implementation
1. Spring Boot starter

## Getting started

1. Creating the Key-Value Store
2. Storing a value.
3. Getting a value.
4. Removing a value.

### Creating the Key-Value Store

Pick one of the Key-Value Store implementations you want to use above. 

If you would have taken the Filesystem implementation the code to create the
Key-Value Store would look like the code below.

```java
  File baseDirectory = new File("mybasedir");
  FilesystemKeyValueStore store = new FilesystemKeyValueStore(baseDirectory);
```

### Storing a value

The example below illustrates how to store a value.

```java
  store.put("mykey", "myvalue");
```

### Getting a value

The example below illustrates how to store a value.

```java
  String value = store.get("mykey");
```

### Deleting a value

The example below illustrates how to delete a value.

```java
  store.delete("mykey");
```

## Using your own key type

By default the Key-Value Store is setup to convert a String based key to the
underlying data type the Key-Value Store expects. You can deliver your own key
mapper that can convert from a data-type of your choice to the underlying data
type.

The example below illustrates how to deliver a Long based key mapper.

```java

  public class LongToStringMapper implements KeyValueStoreMapper<Long, String> {

      public String to(Long to) {
        return to.toString();
      }

      public Long from(String from) {
        return Long.valueOf(from);
      }
  }

  store.setKeyMapper(new LongToStringMapper());

```

## Using your own value type

The only difference with the section above is setting the value mapper instead
of the key mapper.

The method to set the value mapper is seen below.

```java
  store.setValueMapper(new LongtoStringMapper());
```
