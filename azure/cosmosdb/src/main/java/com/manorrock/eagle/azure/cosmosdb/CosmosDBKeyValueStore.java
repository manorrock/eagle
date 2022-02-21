/*
 *  Copyright (c) 2002-2021, Manorrock.com. All Rights Reserved.
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
package com.manorrock.eagle.azure.cosmosdb;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.manorrock.eagle.api.KeyValueStore;

/**
 * A Cosmos DB based KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class CosmosDBKeyValueStore<K, V> implements KeyValueStore<K, V> {
    
    /**
     * Stores the CosmosDB client.
     */
    private final CosmosClient client;
    
    /**
     * Stores the CosmosDB container.
     */
    private final CosmosContainer container;
    
    /**
     * Stores the CosmosDB database.
     */
    private final CosmosDatabase database;

    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     * @param masterKey the master key.
     * @param consistencyLevel the consistency level.
     * @param databaseName the database.
     * @param containerName the container name.
     */
    public CosmosDBKeyValueStore(String endpoint, String masterKey, 
            String consistencyLevel, String databaseName, String containerName) {
        client = new CosmosClientBuilder()
                .endpoint(endpoint)
                .key(masterKey)
                .consistencyLevel(ConsistencyLevel.valueOf(consistencyLevel))
                .buildClient();
        database = client.getDatabase(databaseName);
        container = database.getContainer(containerName);
    }

    @Override
    public void delete(K key) {
        CosmosDBKey cosmosKey = (CosmosDBKey) toKey(key);
        container.deleteItem(
                cosmosKey.getItemId(), 
                cosmosKey.getPartitionKey(),
                cosmosKey.getOptions());
    }

    @Override
    public V get(K key) {
        CosmosDBKey cosmosKey = (CosmosDBKey) toKey(key);
        Object cosmosValue = container.readItem(
                cosmosKey.getItemId(), 
                cosmosKey.getPartitionKey(), 
                String.class);
        return (V) toValue(cosmosValue);
    }

    @Override
    public void put(K key, V value) {
        CosmosDBKey cosmosKey = (CosmosDBKey) toKey(key);
        Object cosmosValue = toValue(value);
        container.upsertItem(
                cosmosValue,
                cosmosKey.getPartitionKey(),
                cosmosKey.getOptions());
    }
}
