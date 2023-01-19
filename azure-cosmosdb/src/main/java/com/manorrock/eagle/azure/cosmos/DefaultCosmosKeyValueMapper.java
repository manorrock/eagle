package com.manorrock.eagle.azure.cosmos;

import com.manorrock.eagle.api.KeyValueMapper;

/**
 * The default Azure Blob Storage KeyValueMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class DefaultCosmosKeyValueMapper implements KeyValueMapper<CosmosKey, byte[]> {
    
    @Override
    public Object fromKey(CosmosKey key) {
        return key;
    }

    @Override
    public Object fromValue(byte[] value) {
        return value;
    }

    @Override
    public CosmosKey toKey(Object underlyingKey) {
        return (CosmosKey) underlyingKey;
    }

    @Override
    public byte[] toValue(Object underlyingValue) {
        return (byte[]) underlyingValue;
    }
}
