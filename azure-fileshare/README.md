# Manorrock Eagle

## Azure File Share implementation

If you want to use Azure File Share as a Key-Value store you will need to do the
following.

1. Add the dependency.
2. Create the FilesKeyValueStore.

### Add the dependency

To start add the following dependency.

```xml
    <dependency>
        <groupId>com.manorrock.eagle.azure</groupId>
        <artifactId>eagle-azure-fileshare</artifactId>
        <version>MY_VERSION</version>
    </dependency>
```

Where MY_VERSION should be replaced wit the version you want to use.

### Create the FilesKeyValueStore

The following code snippet will get your started.

```java
    FilesKeyValueStore store = new FilesKeyValueStore<>(
        "endpoint",
        "shareName",
        "sasToken"
    );
```

Then the rest is using the KeyValueStore APIs.

## Overriding the Azure File Share dependency

Beneath the covers the Azure File Share implementation uses Azure File Share 
library. If your own project uses the Azure File Share library, or any of its 
dependencies you should be aware of this. Note the version of the Azure File
Share library dependency that this implementation uses can be found in the
pom.xml file of this module.

## Factory properties

If you want to create the FilesKeyValueStore using the KeyValueStoreFactory 
use the following properties.

| Key | Value 
| --- | -----
| className | com.manorrock.eagle.azure.files.FilesKeyValueStore
| endpoint | the endpoint
| shareName | the share name
| sasToken | the SAS token
