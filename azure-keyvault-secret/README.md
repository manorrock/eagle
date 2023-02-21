# Manorrock Eagle

## Azure Key Vault Secret implementation

If you want to use Azure Key Vault Secret as a Key-Value store you will
need to do the following.

1. Add the dependency.
2. Create the SecretKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle.azure</groupId>
        <artifactId>eagle-azure-keyvault-secret</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the SecretKeyValueStore

The following code snippet will get your started.

```java
    SecretKeyValueStore store = new SecretKeyValueStore(
        "endpoint"
    );
```

Then the rest is using the KeyValueStore APIs.

## Overriding the Azure Key Vault Secrets dependency

Beneath the covers the Azure Key Vault Secret implementation uses Azure 
Key Vault Secrets library. If your own project uses the Azure Key Vault
Keys library, or any of its dependencies you should be aware of this.
Note the version of the Azure Key Vault Secrets library dependency that 
this implementation uses can be found in the pom.xml file of this module.

## Factory properties

If you want to create the SecretKeyValueStore using the
KeyValueStoreFactory use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.azure.keyvault.secret.SecretKeyValueStore
| endpoint  | the endpoint
