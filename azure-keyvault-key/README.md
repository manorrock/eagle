# Manorrock Eagle

## Azure Key Vault Key implementation

If you want to use Azure Key Vault Keys as a Key-Value store you will
need to do the following.

1. Add the dependency.
2. Create the KeyKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle.azure</groupId>
        <artifactId>eagle-azure-keyvault-key</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the KeyKeyValueStore

The following code snippet will get your started.

```java
    KeyKeyValueStore store = new KeyKeyValueStore(
        "endpoint"
    );
```

Then the rest is using the KeyValueStore APIs.

## Overriding the Azure Key Vault Key dependency

Beneath the covers the Azure Key Vault Key implementation uses Azure 
Key Vault Keys library. If your own project uses the Azure Key Vault
Keys library, or any of its dependencies you should be aware of this.
Note the version of the Azure Key Vault Keys library dependency that 
this implementation uses can be found in the pom.xml file of this module.

## Factory properties

If you want to create the KeyKeyValueStore using the
KeyValueStoreFactory use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.azure.keyvault.key.KeyKeyValueStore
| endpoint | the endpoint
