package com.manorrock.eagle.azure.files;

import com.manorrock.eagle.api.KeyValueMapper;

/**
 * The default Azure Files KeyValueMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class DefaultFilesKeyValueMapper implements KeyValueMapper<String, byte[]> {
    
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
        return underlyingKey.toString();
    }

    @Override
    public byte[] toValue(Object underlyingValue) {
        return (byte[]) underlyingValue;
    }
}
