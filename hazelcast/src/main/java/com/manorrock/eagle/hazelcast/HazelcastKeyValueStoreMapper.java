package com.manorrock.eagle.hazelcast;

import com.manorrock.eagle.api.KeyValueStoreMapper;

/**
 * The Hazelcast KeyValueStoreMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @deprecated 
 */
@Deprecated(since = "23.4.0", forRemoval = true)
public class HazelcastKeyValueStoreMapper implements KeyValueStoreMapper<Object, Object> {
    
    @Override
    public Object fromKey(Object key) {
        return key;
    }

    @Override
    public Object fromValue(Object value) {
        return value;
    }

    @Override
    public Object toKey(Object underlyingKey) {
        return underlyingKey;
    }

    @Override
    public byte[] toValue(Object underlyingValue) {
        return (byte[]) underlyingValue;
    }
}
