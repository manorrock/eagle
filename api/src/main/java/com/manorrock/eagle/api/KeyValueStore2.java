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
package com.manorrock.eagle.api;

/**
 * The KeyValueStore API.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the key type.
 * @param <V> the value type.
 * @param <UK> the underlying key type.
 * @param <UV> the underlying value type.
 */
public interface KeyValueStore2<K, V, UK, UV> {
    
    /**
     * Delete the value.
     * 
     * @param key the key.
     */
    void delete(K key);
        
    /**
     * Get the value.
     * 
     * @param key the key.
     * @return the value.
     */
    V get(K key);

    /**
     * Put the value.
     *
     * @param key the key.
     * @param value the value.
     */
    void put(K key, V value);
    
    /**
     * To key.
     * 
     * @param underlyingKey the underlying key.
     * @return the key.
     */
    default K toKey(UK underlyingKey) {
        return (K) underlyingKey;
    }
    
    /**
     * To underlying key.
     * 
     * @param key the key.
     * @return the underlying key.
     */
    default UK toUnderlyingKey(K key) {
        return (UK) key;
    }
    
    /**
     * To underlying value.
     * 
     * @param value the value.
     * @return the underlying value.
     */
    default UV toUnderlyingValue(V value) {
        return (UV) value;
    }
    
    /**
     * To value.
     * 
     * @param underlyingValue the underlying value.
     * @return the value.
     */
    default V toValue(UV underlyingValue) {
        return (V) underlyingValue;
    }
}
