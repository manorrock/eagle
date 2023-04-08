/*
 *  Copyright (c) 2002-2023, Manorrock.com. All Rights Reserved.
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
package com.manorrock.eagle.azure.keyvault.certificate;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.certificates.CertificateClient;
import com.azure.security.keyvault.certificates.CertificateClientBuilder;
import com.azure.security.keyvault.certificates.models.ImportCertificateOptions;
import com.azure.security.keyvault.certificates.models.KeyVaultCertificateWithPolicy;
import com.manorrock.eagle.api.KeyValueStore;
import java.util.Map;

/**
 * An Azure Key Vault Certificates based KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class CertificateKeyValueStore<K, V> implements KeyValueStore<K, V, String, byte[]> {

    /**
     * Stores the client.
     */
    private final CertificateClient client;
    
    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     */
    public CertificateKeyValueStore(String endpoint) {
        this(endpoint, new DefaultAzureCredentialBuilder().build());
    }

    /**
     * Constructor.
     *
     * @param endpoint the endpoint.
     * @param credential the token credential.
     */
    public CertificateKeyValueStore(String endpoint, TokenCredential credential) {
        client = new CertificateClientBuilder()
                .vaultUrl(endpoint)
                .credential(credential)
                .buildClient();
    }

    @Override
    public void delete(K key) {
        String name = toUnderlyingKey(key);
        client.beginDeleteCertificate(name);
    }

    @Override
    public V get(K key) {
        String name = toUnderlyingKey(key);
        KeyVaultCertificateWithPolicy certificate = client.getCertificate(name);
        V result = null;
        if (certificate != null) {
            CertificateWrapper wrapper = new CertificateWrapper();
            wrapper.setWrapped(certificate);
            result = toValue(wrapper.getBytes());
        }
        return result;
    }

    @Override
    public Map getDelegate() {
        return Map.of("certificateClient", client);
    }

    @Override
    public void put(K key, V value) {
        String name = toUnderlyingKey(key);
        CertificateWrapper wrapper = new CertificateWrapper(toUnderlyingValue(value));
        ImportCertificateOptions options = new ImportCertificateOptions(name, wrapper.getBytes());
        client.importCertificate(options);
    }
}
