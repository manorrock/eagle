# Manorrock Eagle

## ⚠️ Project Archival Notice

This project is part of the Manorrock Sustainability Initiative. We are seeking new maintainers to take over this project. If no maintainers step forward by December 31, 2025, this repository will be archived and moved to the manorrock-attic organization.

### Project Timeline

- **Through December 31, 2025**: Repository remains active while seeking maintainers
- **After December 31, 2025**: If no maintainers found, project moves to manorrock-attic
- **Until December 31, 2030**: Project remains available read-only in the attic
- **After December 31, 2030**: Project may be removed

### Interested in Maintaining This Project?

If you're interested in becoming a maintainer, please see [this GitHub issue](https://github.com/manorrock/eagle/issues/221) for details on how to express your interest and what's involved. Note that new maintainers will need to migrate the project to a new namespace, as the Manorrock branding will remain with Manorrock.com.

**After December 31, 2025**: If this project moves to the manorrock-attic, GitHub issues will no longer be available. If you become interested in maintaining this project after it's archived, please email info@manorrock.com with the subject "Revival Request: [Project Name]".

### More Information

For more information about the Manorrock Projects Sustainability Initiative, please visit our [blog post](https://www.manorrock.com/blog/2025/04/14/manorrock_sustainability_initiative.html).

---

[![build](https://github.com/manorrock/eagle/actions/workflows/build.yml/badge.svg)](https://github.com/manorrock/eagle/actions/workflows/build.yml)

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
  store.put("mykey", "myvalue".getBytes());
```

### Getting a value

The example below illustrates how to get a value.

```java
  String value = new String(store.get("mykey"));
```

### Deleting a value

The example below illustrates how to delete a value.

```java
  store.delete("mykey");
```

## How do I contribute?

See [Contributing](CONTRIBUTING.md)

## Our code of Conduct

See [Code of Conduct](CODE_OF_CONDUCT.md)
