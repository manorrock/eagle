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
package com.manorrock.eagle.path;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import com.manorrock.eagle.api.KeyValueStore;
import java.util.Map;

/**
 * A Path based KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class PathKeyValueStore<K, V> implements KeyValueStore<K, V, Path, byte[]> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PathKeyValueStore.class.getName());
    
    /**
     * Stores the path.
     */
    private final Path basePath;
    
    /**
     * Constructor.
     *
     * @param path the path.
     */
    public PathKeyValueStore(Path path) {
        this.basePath = path;
    }

    @Override
    public void delete(K key) {
        try {
            Path path = (Path) toUnderlyingKey(key);
            Files.deleteIfExists(basePath.resolve(path));
        } catch (IOException ioe) {
            LOGGER.log(WARNING, "An I/O error occured deleting key: " + key, ioe);
        }
    }

    @Override
    public V get(K key) {
        V result = null;
        Path path = (Path) toUnderlyingKey(key);
        if (Files.exists(basePath.resolve(path))) {
            try {
                byte[] bytes = Files.readAllBytes(basePath.resolve(path));
                result = (V) toValue(bytes);
            } catch (IOException ioe) {
                LOGGER.log(WARNING, "Unable to get content for key: " + key, ioe);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getDelegate() {
        return Map.of("basePath", basePath);
    }

    @Override
    public void put(K key, V value) {
        Path path = basePath.resolve((Path) toUnderlyingKey(key));
        if (!Files.exists(path.getParent())) {
            try {
                Files.createDirectories(path);
            } catch (IOException ioe) {
                LOGGER.log(WARNING, "Unable to create directories for key: " + key, ioe);
                return;
            }
        }
        try (OutputStream output = Files.newOutputStream(path)) {
            output.write((byte[]) toUnderlyingValue(value));
            output.flush();
        } catch (IOException ioe) {
            LOGGER.log(WARNING, "Unable to put content for key: " + key, ioe);
        }
    }
}
