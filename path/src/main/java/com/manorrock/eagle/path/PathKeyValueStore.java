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
package com.manorrock.eagle.path;

import com.manorrock.eagle.api.KeyValueStore;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;

/**
 * A Path based KeyValueStore.
 * 
 * <p>
 *  This KeyValueStore is by default implemented with a K and V as Strings. If
 *  you want to change K and/or V you will need to implement the appropriate
 *  from and to methods.
 * </p> 
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class PathKeyValueStore<K, V> implements KeyValueStore<K, V> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PathKeyValueStore.class.getName());
    
    /**
     * Stores the base path.
     */
    private final Path basePath;

    /**
     * Constructor.
     *
     * @param basePath the base path.
     */
    public PathKeyValueStore(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public void delete(K key) {
        try {
            Path path = (Path) fromKey(key);
            Files.deleteIfExists(path);
        } catch (IOException ioe) {
            LOGGER.log(WARNING, "An I/O error occured deleting key: " + key, ioe);
        }
    }

    @Override
    public Object fromKey(K key) {
        return basePath.resolve(key.toString());
    }

    @Override
    public Object fromValue(V value) {
        byte[] result = null;
        try {
            result = value.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            LOGGER.log(WARNING, "Encountered an unsupported encoding", uee);
        }
        return result;
    }

    @Override
    public V get(K key) {
        V result = null;
        Path path = (Path) fromKey(key);
        if (Files.exists(path)) {
            try {
                byte[] bytes = Files.readAllBytes(path);
                result = (V) toValue(bytes);
            } catch (IOException ioe) {
                LOGGER.log(WARNING, "Unable to get content for key: " + key, ioe);
            }
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        Path path = (Path) fromKey(key);
        if (!Files.exists(path.getParent())) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException ioe) {
                LOGGER.log(WARNING, "Unable to create directories for key: " + key, ioe);
                return;
            }
        }
        try (OutputStream output = Files.newOutputStream(path)) {
            output.write((byte[]) fromValue(value));
            output.flush();
        } catch (IOException ioe) {
            LOGGER.log(WARNING, "Unable to put content for key: " + key, ioe);
        }
    }

    @Override
    public K toKey(Object underlyingKey) {
        return (K) ((Path) underlyingKey).toString().substring(basePath.toString().length());
    }

    @Override
    public V toValue(Object underlyingValue) {
        String result = null;
        try {
            result = new String((byte[]) underlyingValue, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            LOGGER.log(WARNING, "Encountered an unsupported encoding", uee);
        }
        return (V) result;
    }
}
