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
package com.manorrock.eagle.azure.files;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareClientBuilder;
import com.azure.storage.file.share.ShareFileClient;
import com.manorrock.eagle.api.KeyValueStore;
import com.manorrock.eagle.api.KeyValueStoreMapper;
import com.manorrock.eagle.common.IdentityMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Azure File Share based KeyValueStore.
 *
 * <p>
 *  Note the default keyMapper is setup assuming the K type is String, the 
 *  default valueMapper is setup assuming the V type is byte-array. If that is
 *  not the case make sure to deliver the appropriate mapper.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class FilesKeyValueStore<K, V> implements KeyValueStore<K, V, String, byte[]> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FilesKeyValueStore.class.getPackage().getName());

    /**
     * Stores the client.
     */
    private final ShareClient client;

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
     * @param shareName the share name.
     * @param sasToken the SAS token.
     */
    public FilesKeyValueStore(String endpoint, String shareName, String sasToken) {
        client = new ShareClientBuilder()
                .endpoint(endpoint)
                .shareName(shareName)
                .sasToken(sasToken)
                .buildClient();
        keyMapper = new IdentityMapper();
        valueMapper = new IdentityMapper();
    }

    @Override
    public void delete(K key) {
        String name = (String) keyMapper.to(key);
        client.deleteFile(name);
    }

    @Override
    public V get(K key) {
        String filename = (String) keyMapper.to(key);
        V result = null;
        try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            client.getFileClient(filename).download(outputStream);
            result = (V) valueMapper.from(outputStream.toByteArray());
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Unable to download file: {0}", filename);
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        String filename = (String) keyMapper.to(key);
        byte[] bytes = (byte[]) valueMapper.to(value);
        try ( ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            ShareFileClient fileClient = client.createFile(filename, bytes.length);
            fileClient.upload(inputStream, bytes.length);
        } catch (IOException ioe) {
            LOGGER.log(Level.WARNING, "Unable to upload file: {0}", filename);
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
