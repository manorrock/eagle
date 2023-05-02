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
package com.manorrock.eagle.maven;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.Redirect.ALWAYS;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import com.manorrock.eagle.api.KeyValueStore;
import java.util.Map;

/**
 * The Maven repository KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the jey type.
 * @param <V> the value type.
 */
public class MavenRepositoryKeyValueStore<K, V> implements KeyValueStore<K, V, String, byte[]> {

    /**
     * Stores the HttpClient.
     */
    private final HttpClient client;

    /**
     * Stores the URI.
     */
    private final URI uri;

    /**
     * Constructor.
     *
     * @param uri the URI.
     */
    public MavenRepositoryKeyValueStore(URI uri) {
        this.uri = uri;
        this.client = HttpClient.newBuilder().followRedirects(ALWAYS).build();
    }

    @Override
    public void delete(K key) {
        String path = toUnderlyingKey(key);
        mavenRepoDelete(path);
    }

    @Override
    public V get(K key) {
        String path = toUnderlyingKey(key);
        return (V) toValue(mavenRepoGet(path));
    }

    @Override
    public Map<String, Object> getDelegate() {
        return Map.of("uri", uri, "httpClient", client);
    }

    @Override
    public void put(K key, V value) {
        String path = toUnderlyingKey(key);
        byte[] content = toUnderlyingValue(value);
        mavenRepoPut(path, content);
    }

    @Override
    public String toUnderlyingKey(K key) {
        return key.toString();
    }

    @Override
    public byte[] toUnderlyingValue(V value) {
        return (byte[]) value;
    }

    /**
     * Construct the artifact path for the given path.
     *
     * @param path the path.
     */
    private String constructArtifactPath(String path) {
        String resolvedPath
                = path.substring(0, path.indexOf("/")).replace(".", "/")
                + path.substring(path.indexOf("/"));
        return resolvedPath;
    }

    /**
     * Delete the artifact at the given path.
     *
     * @param path the path.
     */
    private void mavenRepoDelete(String path) {
        try {
            String artifactPath = constructArtifactPath(path);
            HttpRequest request = HttpRequest
                    .newBuilder(uri.resolve(artifactPath))
                    .DELETE()
                    .build();
            client.send(request, BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            // swalloing this up on purpose
        }
    }

    /**
     * Get the artifact at the given path.
     *
     * @param path the path.
     * @return the content, or null if not found or HTTP status code other than
     * 200.
     */
    private byte[] mavenRepoGet(String path) {
        byte[] result = null;
        try {
            String artifactPath = constructArtifactPath(path);
            HttpRequest request = HttpRequest
                    .newBuilder(uri.resolve(artifactPath))
                    .GET()
                    .build();
            HttpResponse<byte[]> response = client.send(request, BodyHandlers.ofByteArray());
            if (response.statusCode() == 200) {
                result = response.body();
            }
        } catch (IOException | InterruptedException e) {
            // swalloing this up on purpose
        }
        return result;
    }

    /**
     * Put the artifact at the given path.
     *
     * @param path the path.
     * @param content the content.
     */
    private void mavenRepoPut(String path, byte[] content) {
        try {
            String artifactPath = constructArtifactPath(path);
            HttpRequest request = HttpRequest
                    .newBuilder(uri.resolve(artifactPath))
                    .PUT(BodyPublishers.ofByteArray(content))
                    .build();
            client.send(request, BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            // swalloing this up on purpose
        }
    }
}
