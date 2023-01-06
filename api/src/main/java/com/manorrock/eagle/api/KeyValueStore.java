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
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public interface KeyValueStore<K, V> {
    
    /**
     * Delete the value.
     * 
     * @param key the key.
     */
    void delete(K key);
    
    /**
     * Convert from the key to the underlying key.
     * 
     * @param key the key.
     * @return the underlying key.
     */
    default Object fromKey(K key) {
        return key;
    }
    
    /**
     * Convert from the value to the underlying value.
     * 
     * @param value the value.
     * @return the underlying value.
     */
    default Object fromValue(V value) {
        return value;
    }
    
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
     * Convert the underlying key to the key.
     * 
     * @param underlyingKey the underlying key.
     * @return the key.
     */
    default K toKey(Object underlyingKey) {
        return (K) underlyingKey;
    }
    
    /**
     * Convert from the underlying value to the value.
     * 
     * @param underlyingValue the underlying value.
     * @return the value.
     */
    default V toValue(Object underlyingValue) {
        return (V) underlyingValue;
    }
}
