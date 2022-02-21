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
package com.manorrock.eagle.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.manorrock.eagle.api.KeyValueStore;
import com.manorrock.eagle.api.KeyValueStoreMapper;
import com.manorrock.eagle.common.StringToByteArrayMapper;
import java.util.Map;

/**
 * A Hazelcast based KeyValueStore.
 *
 * <p>
 * Note the default keyMapper is setup assuming the K type is String, the
 * default valueMapper is setup assuming the V type is String. If that is not
 * the case make sure to deliver the appropriate mapper.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 * @param <KU> the type of the underlying key.
 */
public class HazelcastKeyValueStore<K, V, KU, VU> implements KeyValueStore<K, V, KU> {

    /**
     * Stores the Hazelcast instance.
     */
    private HazelcastInstance hazelcast;

    /**
     * Stores the Hazelcast map.
     */
    private Map hazelcastMap;

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
     * @param name the name.
     */
    public HazelcastKeyValueStore(String name) {
        this.keyMapper = new StringToByteArrayMapper();
        this.valueMapper = new StringToByteArrayMapper();
        Config config = new Config();
        config.setInstanceName(name);
        hazelcast = Hazelcast.newHazelcastInstance(config);
        hazelcastMap = hazelcast.getMap(name);
    }

    @Override
    public void delete(K key) {
        hazelcastMap.remove(keyMapper.to(key));
    }

    @Override
    public V get(K key) {
        V result = null;
        Object value = hazelcastMap.get(keyMapper.to(key));
        if (value != null) {
            result = (V) valueMapper.from(value);
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        hazelcastMap.put(keyMapper.to(key), valueMapper.to(value));
    }

    @Override
    public void setKeyMapper(KeyValueStoreMapper<K, KU> keyMapper) {
        this.keyMapper = keyMapper;
    }
}
