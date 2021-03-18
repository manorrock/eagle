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
package com.manorrock.eagle.azure.blob;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.manorrock.eagle.api.KeyValueStore;
import com.manorrock.eagle.api.KeyValueStoreMapper;
import com.manorrock.eagle.common.IdentityMapper;
import com.manorrock.eagle.common.StringToByteArrayMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Azure Blob Storage based KeyValueStore.
 *
 * <p>
 *  Note the default keyMapper is setup assuming the K type is String, the 
 *  default valueMapper is setup assuming the V type is String. If that is not
 *  the case make sure to deliver the appropriate mapper.
 * </p>
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
    private BlobServiceClient client;

    /**
     * Stores the container.
     */
    private BlobContainerClient container;

    /**
     * Stores the key mapper.
     */
    private KeyValueStoreMapper keyMapper;

    /**
     * Stores the value mapper.
     */
    private KeyValueStoreMapper valueMapper;

    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     * @param containerName the container name.
     */
    public BlobKeyValueStore(String endpoint, String containerName) {
        this(endpoint, containerName, new DefaultAzureCredentialBuilder().build());
    }

    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     * @param containerName the container name.
     * @param credential the token credential.
     */
    public BlobKeyValueStore(String endpoint, String containerName, TokenCredential credential) {
        client = new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .credential(credential)
                .buildClient();
        container = client.getBlobContainerClient(containerName);
        keyMapper = new IdentityMapper();
        valueMapper = new StringToByteArrayMapper();
    }

    @Override
    public void delete(K key) {
        String blobName = (String) keyMapper.to(key);
        BlobClient blob = container.getBlobClient(blobName);
        blob.delete();
    }

    @Override
    public V get(K key) {
        String blobName = (String) keyMapper.to(key);
        BlobClient blob = container.getBlobClient(blobName);
        V result = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            blob.download(outputStream);
            result = (V) valueMapper.from(outputStream.toByteArray());
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Unable to download blob: {0}", blobName);
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        String blobName = (String) keyMapper.to(key);
        BlobClient blob = container.getBlobClient(blobName);
        byte[] bytes = (byte[]) valueMapper.to(value);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            blob.upload(inputStream, bytes.length);
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Unable to upload blob: {0}", blobName);
        }
    }

    @Override
    public void setKeyMapper(KeyValueStoreMapper<K, String> keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public void setValueMapper(KeyValueStoreMapper<V, byte[]> valueMapper) {
        this.valueMapper = valueMapper;
    }
}
