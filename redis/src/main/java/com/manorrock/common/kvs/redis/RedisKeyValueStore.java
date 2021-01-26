/*
 *  Copyright (c) 2002-2020, Manorrock.com. All Rights Reserved.
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
package com.manorrock.common.kvs.redis;

import com.manorrock.common.kvs.api.KeyValueMapper;
import com.manorrock.common.kvs.api.KeyValueStore;
import com.manorrock.common.kvs.common.IdentityMapper;
import com.manorrock.common.kvs.common.StringToByteArrayMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.RedisCodec;
import java.net.URI;
import java.nio.ByteBuffer;

/**
 * A Redis based KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class RedisKeyValueStore<K, V> implements KeyValueStore<K, V> {
    
    /**
     * Stores the Redis client.
     */
    private RedisClient client;
    
    /**
     * Stores the Redis connection.
     */
    private StatefulRedisConnection<K, V> connection;

    /**
     * Stores the key mapper.
     */
    private KeyValueMapper keyMapper;

    /**
     * Stores the value mapper.
     */
    private KeyValueMapper valueMapper;

    /**
     * Constructor.
     *
     * @param uri the URI.
     */
    public RedisKeyValueStore(URI uri) {
        this.keyMapper = new StringToByteArrayMapper();
        this.valueMapper = new IdentityMapper();
        client = RedisClient.create(uri.toString());
        connection = client.connect(new RedisCodec<K, V>() {
            @Override
            public K decodeKey(ByteBuffer bb) {
                byte[] bytes = new byte[bb.remaining()];
                bb.get(bytes);
                return (K) keyMapper.from(bytes);
            }

            @Override
            public V decodeValue(ByteBuffer bb) {
                byte[] bytes = new byte[bb.remaining()];
                bb.get(bytes);
                return (V) valueMapper.from(bytes);
            }

            @Override
            public ByteBuffer encodeKey(K k) {
                return ByteBuffer.wrap((byte[]) keyMapper.to(k));
            }

            @Override
            public ByteBuffer encodeValue(V v) {
                return ByteBuffer.wrap((byte[]) valueMapper.to(v));
            }
        });
    }

    /**
     * Delete the given key.
     *
     * @param key the key.
     */
    @Override
    public void delete(K key) {
        connection.sync().del(key);
    }

    /**
     * @see KeyValueStore#get(java.lang.Object)
     */
    @Override
    public V get(K key) {
        return connection.sync().get(key);
    }

    /**
     * @see KeyValueStore#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(K key, V value) {
        connection.sync().set(key, value);
    }

    @Override
    public void setKeyMapper(KeyValueMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public void setValueMapper(KeyValueMapper valueMapper) {
        this.valueMapper = valueMapper;
    }
}
