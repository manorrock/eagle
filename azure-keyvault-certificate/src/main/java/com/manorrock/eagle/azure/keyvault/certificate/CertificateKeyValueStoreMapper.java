package com.manorrock.eagle.azure.keyvault.certificate;

import com.manorrock.eagle.api.KeyValueStoreMapper;

/**
 * The default Azure KeyVault Certificate KeyValueStoreMapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class CertificateKeyValueStoreMapper implements KeyValueStoreMapper<String, byte[]> {
    
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
