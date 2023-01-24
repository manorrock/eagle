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
package com.manorrock.eagle.azure.files;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareClientBuilder;
import com.azure.storage.file.share.ShareFileClient;
import com.manorrock.eagle.api.KeyValueMapper;
import com.manorrock.eagle.api.KeyValueStore;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Azure File Share based KeyValueStore.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class FilesKeyValueStore<K, V> implements KeyValueStore<K, V> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FilesKeyValueStore.class.getPackage().getName());

    /**
     * Stores the client.
     */
    private final ShareClient client;
    
    /**
     * Stores the mapper.
     */
    private KeyValueMapper mapper;

    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     * @param shareName the share name.
     * @param sasToken the SAS token.
     */
    public FilesKeyValueStore(String endpoint, String shareName, String sasToken) {
        client = new ShareClientBuilder()
                .endpoint(endpoint)
                .shareName(shareName)
                .sasToken(sasToken)
                .buildClient();
        this.mapper = new DefaultFilesKeyValueMapper();
    }

    /**
     * Constructor.
     *
     * @param mapper the mapper.
     * @param endpoint the endpoint.
     * @param shareName the share name.
     * @param sasToken the SAS token.
     */
    public FilesKeyValueStore(KeyValueMapper mapper, String endpoint, String shareName, String sasToken) {
        this(endpoint, shareName, sasToken);
        this.mapper = mapper;
    }

    @Override
    public void delete(K key) {
        String name = (String) mapper.fromKey(key);
        client.deleteFile(name);
    }

    @Override
    public V get(K key) {
        String filename = (String) mapper.fromKey(key);
        V result = null;
        try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            client.getFileClient(filename).download(outputStream);
            result = (V) mapper.toValue(outputStream.toByteArray());
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Unable to download file: {0}", filename);
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        String filename = (String) mapper.fromKey(key);
        byte[] bytes = (byte[]) mapper.fromValue(value);
        try ( ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            ShareFileClient fileClient = client.createFile(filename, bytes.length);
            fileClient.upload(inputStream, bytes.length);
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Unable to upload file: {0}", filename);
        }
    }
}
