# Manorrock Eagle

## CosmosDB implementation

If you want to use CosmosDB as a Key-Value store you will need to do the following.

1. Add the dependency.
2. Create the CosmosDBKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle.azure</groupId>
        <artifactId>eagle-azure-cosmosdb</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the CosmosDBKeyValueStore

The following code snippet will get your started.

```java
    CosmosDBKeyValueStore store = new CosmosDBKeyValueStore(
        "endpoint",
        "masterKey",
        "consistencyLevel",
        "databaseName",
        "containerName"
    );
```

Then the rest is using the KeyValueStore APIs.

## Overriding the Azure Cosmos client dependency

Beneath the covers the CosmosDB implementation uses Azure Cosmos client. If your
own project uses the Azure Cosmos client, or any of its dependencies you should
be aware of this. Note the version of the Azure Cosmos client dependency that 
this implementation uses can be found in the pom.xml file of this module.

## Factory properties

If you want to create the CosmosDBKeyValueStore using the KeyValueStoreFactory 
use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.azure.cosmosdb.CosmosDBKeyValueStore
| endpoint | the endpoint
| masterKey | the master key
| consistencyLevel | the consistency level
| databaseName | the database name
| containerName | the contaner name
