/*
 *  Copyright (c) 2002-2023, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *      3. Neither the name of the copyright holder nor the names of its 
 *         contributors may be used to endorse or promote products derived from
 *         this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.eagle.factory;

import com.azure.storage.common.StorageSharedKeyCredential;
import com.manorrock.eagle.azure.blob.BlobKeyValueStore;
import com.manorrock.eagle.azure.cosmos.CosmosKeyValueStore;
import com.manorrock.eagle.coherence.CoherenceKeyValueStore;
import com.manorrock.eagle.hazelcast.HazelcastKeyValueStore;
import com.manorrock.eagle.redis.RedisKeyValueStore;
import io.lettuce.core.RedisURI;
import java.net.URI;
import java.util.Properties;
import com.manorrock.eagle.api.KeyValueStore;

/**
 * A KeyValueStore factory.
 *
 * <p>
 * This class delivers you with a KeyValueStore factory that takes a
 * configuration map and produces a KeyValueStore instance.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public final class KeyValueStoreFactory {

    /**
     * Constructor.
     */
    private KeyValueStoreFactory() {
    }

    /**
     * Create the Azure Blob Storage KeyValueStore.
     *
     * @param properties the configuration properties.
     * @return the KeyValueStore or null if it could not be created.
     */
    private static KeyValueStore<?,?, ?, ?> getBlobKeyValueStore(Properties properties) {
        return new BlobKeyValueStore<>(
                properties.getProperty("endpoint"),
                properties.getProperty("containerName"),
                new StorageSharedKeyCredential(
                        properties.getProperty("accountName"),
                        properties.getProperty("accountKey")
                ));
    }

    /**
     * Create the Coherence KeyValueStore.
     *
     * @param properties the configuration properties.
     * @return the KeyValueStore or null if it could not be created.
     */
    private static KeyValueStore<?,?,?,?> getCoherenceMapKeyValueStore(Properties properties) {
        return new CoherenceKeyValueStore<>(properties.getProperty("name"));
    }

    /**
     * Create the CosmosDB KeyValueStore.
     *
     * @param properties the configuration properties
     * @return the KeyValueStore or null if it could not be created.
     */
    private static KeyValueStore<?,?,?,?> getCosmosDBKeyValueStore(Properties properties) {
        return new CosmosKeyValueStore<>(
                properties.getProperty("endpoint"),
                properties.getProperty("masterKey"),
                properties.getProperty("consistencyLevel"),
                properties.getProperty("databaseName"),
                properties.getProperty("containerName"));
    }

    /**
     * Create the Hazelcast KeyValueStore.
     *
     * @param properties the configuration properties.
     * @return the KeyValueStore or null if it could not be created.
     */
    private static KeyValueStore<?,?,?,?> getHazelcastKeyValueStore(Properties properties) {
        return new HazelcastKeyValueStore<>(properties.getProperty("name"));
    }

    /**
     * Get the KeyValueStore.
     *
     * @param properties the configuration properties.
     * @return the KeyValueStore, or null if unable to create one.
     */
    public static KeyValueStore<?,?,?,?> getKeyValueStore(Properties properties) {
        KeyValueStore<?,?,?,?> result = null;
        String className = (String) properties.getProperty("className");
        if (className != null) {
            switch (className) {
                case "com.manorrock.eagle.azure.blob.BlobKeyValueStore":
                    result = getBlobKeyValueStore(properties);
                    break;
                case "com.manorrock.eagle.coherence.CoherenceKeyValueStore":
                    result = getCoherenceMapKeyValueStore(properties);
                    break;
                case "com.manorrock.eagle.azure.cosmosdb.CosmosDBKeyValueStore":
                    result = getCosmosDBKeyValueStore(properties);
                    break;
                case "com.manorrock.eagle.hazelcast.HazelcastKeyValueStore":
                    result = getHazelcastKeyValueStore(properties);
                    break;
                case "com.manorrock.eagle.redis.RedisKeyValueStore":
                    result = getRedisKeyValueStore(properties);
                    break;
            }
        }
        return result;
    }

    /**
     * Create the Redis KeyValueStore.
     *
     * @param properties the configuration properties.
     * @return the KeyValueStore or null if it could not be created.
     */
    private static KeyValueStore<?,?,?,?> getRedisKeyValueStore(Properties properties) {
        URI uri = RedisURI.Builder
                .redis(properties.getProperty("hostname"))
                .withPort(Integer.valueOf(properties.getProperty("portNumber", "6379")))
                .withSsl(Boolean.valueOf(properties.getProperty("ssl", "false")))
                .withPassword(properties.getProperty("password").toCharArray())
                .build()
                .toURI();
        return new RedisKeyValueStore<byte[], byte[]>(uri);
    }
}
