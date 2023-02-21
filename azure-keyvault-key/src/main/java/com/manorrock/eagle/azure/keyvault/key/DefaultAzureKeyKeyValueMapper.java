package com.manorrock.eagle.azure.keyvault.key;

import com.manorrock.eagle.api.KeyValueMapper;

/**
 * The default Azure KeyVault Key KeyValueMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class DefaultAzureKeyKeyValueMapper implements KeyValueMapper<String, byte[]> {
    
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
