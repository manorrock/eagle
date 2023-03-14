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

import com.azure.security.keyvault.certificates.models.KeyVaultCertificateWithPolicy;

/**
 * A wrapper to deal with certificates.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @deprecated 
 */
@Deprecated(since = "23.4.0", forRemoval = true)
class CertificateWrapper {

    /**
     * Stores the certificate bytes.
     */
    private byte[] bytes;

    /**
     * Stores the wrapped certificate.
     */
    private KeyVaultCertificateWithPolicy wrapped;

    /**
     * Constructor.
     */
    public CertificateWrapper() {
    }
    
    /**
     * Constructor.
     * 
     * @param bytes the certificate bytes.
     */
    public CertificateWrapper(byte[] bytes) {
        this.bytes = bytes;
    }
    
    /**
     * Get the certificate bytes.
     * 
     * @return the certificate bytes.
    */
    public byte[] getBytes() {
        byte[] result = bytes;
        if (result == null) {
            result = wrapped.getCer();
        }
        return result;
    }

    /**
     * Set the certificate bytes.
     * 
     * @param bytes the bytes.
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Set the wrapped certificate.
     * 
     * @param wrapped the certificate
     */
    public void setWrapped(KeyVaultCertificateWithPolicy wrapped) {
        this.wrapped = wrapped;
    }
}
