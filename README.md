# Manorrock Eagle

_Note this project has gone into passive mode. See below for an explanation_

This project delivers you with a Key-Value Store abstraction.

Our current list of features/implementations available:

1. [Azure Blob Storage implementation](azure-blob/README.md)
1. [Azure Cosmos DB implementation](azure-cosmosdb/README.md)
1. [Azure File Share implementation](azure-fileshare/README.md)
1. [Azure KeyVault Certificate implementation](azure-keyvault-certificate/README.md)
1. [Azure KeyVault Key implementation](azure-keyvault-key/README.md)
1. [Azure KeyVault Secret implementation](azure-keyvault-secret/README.md)
1. [Factory functionality](factory/README.md)
1. [Filesystem implementation](filesystem/README.md)
1. [Hazelcast implementation](hazelcast/README.md)
1. [Manrrock Calico implementation](calico/README.md)
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
  FilesystemKeyValueStore<String, byte[]> store = new FilesystemKeyValueStore<>(baseDirectory);
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

## What is passive mode?

A project can go into passive mode for either of two reasons. Either the project
is feature complete and no active development is needed. Or the project is no
longer considered a priority. Whatever the reason the end result is the same.

This means:

1. No more scheduled monthly releases.
2. If a bug is filed it is addressed on a best effort basis.
3. No new features are anticipated.
4. Releases are only cut on a needs basis and not more than once a month.
5. If you want your bug or feature to receive attention sponsoring is your best bet.
