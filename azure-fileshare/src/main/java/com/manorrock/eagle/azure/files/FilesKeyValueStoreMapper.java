package com.manorrock.eagle.azure.files;

import com.manorrock.eagle.api.KeyValueStoreMapper;

/**
 * The default Azure Files KeyValueStoreMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class FilesKeyValueStoreMapper implements KeyValueStoreMapper<String, byte[]> {
    
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
