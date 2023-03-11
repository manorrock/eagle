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
package com.manorrock.eagle.chroniclemap;

import com.manorrock.eagle.api.KeyValueStore;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import com.manorrock.eagle.api.KeyValueStoreMapper;

/**
 * A ChronicleMap based KeyValueStore.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 * @deprecated
 */
@Deprecated(since = "23.3.0", forRemoval = true)
public class ChronicleMapKeyValueStore<K, V> implements KeyValueStore<K, V, ChronicleMapKeyValueStoreMapper> {
    
    /**
     * Stores the ChronicleMap.
     */
    private final ChronicleMap chronicleMap;
    
    /**
     * Stores the mapper.
     */
    private final KeyValueStoreMapper mapper;

    /**
     * Constructor.
     *
     * @param name the name.
     * @param maxSize the max size
     */
    public ChronicleMapKeyValueStore(String name, long maxSize) {
        this(name, maxSize, byte[].class, byte[].class);
    }
    
    /**
     * Constructor.
     *
     * @param name the name.
     * @param maxSize the max size.
     * @param keyClass the key class.
     * @param valueClass the value class.
     */
    public ChronicleMapKeyValueStore(String name, long maxSize, Class keyClass, Class valueClass) {
        this(new ChronicleMapKeyValueStoreMapper(), name, maxSize, keyClass, valueClass);
    }
    
    /**
     * Constructor.
     *
     * @param mapper the KeyValueStoreMapper
     * @param name the name.
     * @param maxSize the max size.
     * @param keyClass the key class.
     * @param valueClass the value class.
     */
    public ChronicleMapKeyValueStore(KeyValueStoreMapper mapper, String name, long maxSize, Class keyClass, Class valueClass) {
        chronicleMap = ChronicleMapBuilder
                .of(keyClass, valueClass)
                .averageKey(name.getBytes())
                .averageValue(name.getBytes())
                .entries(maxSize)
                .create();
        this.mapper = mapper;
    }

    @Override
    public void delete(K key) {
        chronicleMap.remove(mapper.fromKey(key));
    }

    @Override
    public V get(K key) {
        V result = null;
        Object value = chronicleMap.get(mapper.fromKey(key));
        if (value != null) {
            result = (V) mapper.toValue(value);
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        chronicleMap.put(mapper.fromKey(key), mapper.fromValue(value));
    }
}
