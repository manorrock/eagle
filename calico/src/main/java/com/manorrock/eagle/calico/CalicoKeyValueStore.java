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
package com.manorrock.eagle.calico;

import com.manorrock.eagle.api.KeyValueStore2;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * A Manorrock Calico based KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the key type.
 * @param <V> the value type.
 */
public class CalicoKeyValueStore<K, V> implements KeyValueStore2<K, V, String, byte[]> {

    /**
     * Stores the base URI.
     */
    private final URI baseUri;

    /**
     * Stores the HttpClient.
     */
    private final HttpClient client;

    /**
     * Constructor.
     *
     * @param baseUri the URI.
     */
    public CalicoKeyValueStore(URI baseUri) {
        this.baseUri = baseUri;
        client = HttpClient.newHttpClient();
    }

    @Override
    public void delete(K key) {
        deleteFile(toUnderlyingKey(key));
    }

    /**
     * Delete the file.
     *
     * @param path the path.
     */
    private void deleteFile(String path) {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(baseUri.resolve(URI.create(path)))
                    .DELETE()
                    .build();
            client.send(request, BodyHandlers.discarding());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public V get(K key) {
        V result = null;
        byte[] underLyingValue = getFile(toUnderlyingKey(key));
        if (underLyingValue != null) {
            result = toValue(underLyingValue);
        }
        return result;
    }

    /**
     * Get the file.
     *
     * @param path.
     * @return the file content, or null if not found.
     */
    private byte[] getFile(String path) {
        byte[] result = null;
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(baseUri.resolve(URI.create(path)))
                    .build();
            HttpResponse<byte[]> response = client.send(request, BodyHandlers.ofByteArray());
            if (response.statusCode() < 400) {
                result = response.body();
            }
        } catch (IOException | InterruptedException ex) {
            result = null;
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        if (getFile(toUnderlyingKey(key)) == null) {
            createFile(toUnderlyingKey(key), toUnderlyingValue(value));
        } else {
            updateFile(toUnderlyingKey(key), toUnderlyingValue(value));
        }
    }

    /**
     * Create the file.
     *
     * @param path the path
     * @param conten the content.
     */
    private void createFile(String path, byte[] content) {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(baseUri.resolve(URI.create(path)))
                    .POST(BodyPublishers.ofByteArray(content))
                    .build();
            client.send(request, BodyHandlers.discarding());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Update the file.
     *
     * @param path the path
     * @param conten the content.
     */
    private void updateFile(String path, byte[] content) {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(baseUri.resolve(URI.create(path)))
                    .PUT(BodyPublishers.ofByteArray(content))
                    .build();
            client.send(request, BodyHandlers.discarding());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toUnderlyingKey(K key) {
        return key.toString();
    }

    @Override
    public byte[] toUnderlyingValue(V value) {
        return (byte[]) value;
    }
}
