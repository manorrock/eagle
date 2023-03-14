package com.manorrock.eagle.azure.keyvault.key;

import com.manorrock.eagle.api.KeyValueStoreMapper;

/**
 * The default Azure KeyVault Key KeyValueStoreMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @deprecated 
 */
@Deprecated(since = "23.4.0", forRemoval = true)
public class KeyKeyValueStoreMapper implements KeyValueStoreMapper<String, byte[]> {
    
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
