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
package com.manorrock.eagle.azure.blob;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.manorrock.eagle.api.KeyValueStore;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Azure Blob Storage based KeyValueStore.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class BlobKeyValueStore<K, V> implements KeyValueStore<K, V, String, byte[]> {
    
    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(BlobKeyValueStore.class.getPackage().getName());

    /**
     * Stores the client.
     */
    private final BlobServiceClient client;

    /**
     * Stores the container.
     */
    private final BlobContainerClient container;
    
    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     * @param containerName the container name.
     * @param credential the storage shared key credential.
     */
    public BlobKeyValueStore(String endpoint, String containerName, StorageSharedKeyCredential credential) {
        client = new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .credential(credential)
                .buildClient();
        container = client.getBlobContainerClient(containerName);
    }

    @Override
    public void delete(K key) {
        String blobName = toUnderlyingKey(key);
        BlobClient blob = container.getBlobClient(blobName);
        blob.delete();
    }

    @Override
    public V get(K key) {
        String blobName = toUnderlyingKey(key);
        BlobClient blob = container.getBlobClient(blobName);
        V result = null;
        if (blob.exists()) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                blob.downloadStream(outputStream);
                result = toValue(outputStream.toByteArray());
            } catch (IOException ioe) {
                LOGGER.log(Level.WARNING, "Unable to download blob: {0}", blobName);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getDelegate() {
        return Map.of("blobServiceCLient", client, "blobContainerClient", container);
    }

    @Override
    public void put(K key, V value) {
        String blobName = toUnderlyingKey(key);
        BlobClient blob = container.getBlobClient(blobName);
        byte[] bytes = toUnderlyingValue(value);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            blob.upload(inputStream, bytes.length);
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Unable to upload blob: {0}", blobName);
        }
    }
}
