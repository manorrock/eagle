_This project will be archived Jan 1st, 2023 and removed Jan 1st, 2024 if nobody
 steps up to take over development_

# Manorrock Eagle

This project delivers you with a Key-Value Store abstraction.

Our current list of features/implementations available:

1. [Azure Cosmos DB implementation](azure/cosmosdb/README.md)
1. [Azure Blob Storage implementation](azure/blob/README.md)
1. [Azure File Share implementation](azure/fileshare/README.md)
1. [Azure KeyVault Certificate implementation](azure/keyvault-certificate/README.md)
1. [Azure KeyVault Key implementation](azure/keyvault-key/README.md)
1. [Azure KeyVault Secret implementation](azure/keyvault-secret/README.md)
1. [Factory functionality](factory/README.md)
1. [Filesystem implementation](filesystem/README.md)
1. [Hazelcast implementation](hazelcast/README.md)
1. [Oracle Coherence implementation](coherence/README.md)
1. [Path implementation](path/README.md)
1. [Redis implementation](redis/README.md)

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
