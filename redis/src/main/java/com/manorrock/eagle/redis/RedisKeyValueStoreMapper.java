package com.manorrock.eagle.redis;

import com.manorrock.eagle.api.KeyValueStoreMapper;

/**
 * The Redis KeyValueStoreMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class RedisKeyValueStoreMapper implements KeyValueStoreMapper<String, byte[]> {
    
    @Override
    public Object fromKey(String key) {
        return key;
    }

    @Override
    public Object fromValue(byte[] value) {
        return value;
    }

    @Override
    public String toKey(Object underlyingKey) {
        return (String) underlyingKey;
    }

    @Override
    public byte[] toValue(Object underlyingValue) {
        return (byte[]) underlyingValue;
    }
}
