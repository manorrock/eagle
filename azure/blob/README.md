# Manorrock Eagle

## Azure Blob Storage implementation

If you want to use Azure Blob Storage as a Key-Value store you will need to do
the following.

1. Add the dependency.
2. Create the BlobKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle.azure</groupId>
        <artifactId>eagle-azure-blob</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the BlobKeyValueStore

The following code snippet will get your started.

```java
    BlobKeyValueStore store = new BlobKeyValueStore(
        "endpoint",
        "containerName"
    );
```

Then the rest is using the KeyValueStore APIs.

## Overriding the Azure Cosmos client dependency

Beneath the covers the Azure Blob Storage implementation uses Azure Blob Storage
client. If your own project uses the Azure Blob Storage client, or any of its 
dependencies you should be aware of this. Note the version of the Azure Blob 
Storage client dependency that this implementation uses can be found in the
pom.xml file of this module.

## Factory properties

If you want to create the BlobKeyValueStore using the KeyValueStoreFactory 
use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.azure.blob.BlobKeyValueStore
| endpoint | the endpoint
| containerName | the container name
