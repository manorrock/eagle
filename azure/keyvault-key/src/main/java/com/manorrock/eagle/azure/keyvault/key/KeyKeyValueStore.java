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
package com.manorrock.eagle.azure.keyvault.key;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.models.ImportKeyOptions;
import com.azure.security.keyvault.keys.models.JsonWebKey;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import com.manorrock.eagle.api.KeyValueStore;

/**
 * An Azure KeyVault Key based KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class KeyKeyValueStore<K, V> implements KeyValueStore<K, V> {

    /**
     * Stores the client.
     */
    private KeyClient client;

    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     */
    public KeyKeyValueStore(String endpoint) {
        this(endpoint, new DefaultAzureCredentialBuilder().build());
    }

    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     * @param credential the token credential.
     */
    public KeyKeyValueStore(String endpoint, TokenCredential credential) {
        client = new KeyClientBuilder()
                .vaultUrl(endpoint)
                .credential(credential)
                .buildClient();
    }

    @Override
    public void delete(K key) {
        String name = (String) toKey(key);
        client.beginDeleteKey(name);
    }

    @Override
    public V get(K key) {
        String name = (String) toKey(key);
        KeyVaultKey keyVaultKey = client.getKey(name);
        V result = null;
        if (keyVaultKey != null && keyVaultKey.getKey()!= null) {
            result = (V) toValue(keyVaultKey.getKey());
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        String name = (String) toKey(key);
        JsonWebKey webKey = (JsonWebKey) fromValue(value);
        ImportKeyOptions options = new ImportKeyOptions(name, webKey);
        client.importKey(options);
    }
}
