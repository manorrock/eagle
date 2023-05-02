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
package com.manorrock.eagle.git;

import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the Git repository KeyValueStore.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
class GitRepositoryKeyValueStoreTest {
    
    /**
     * Stores the base URI.
     */
    private final String BASE_URI = "http://localhost:8080/repositories/test/";
    
    /**
     * Test delete method.
     */
    @Test
    void testDelete() {
        GitRepositoryKeyValueStore<String, byte[]> store = new GitRepositoryKeyValueStore<>(
                URI.create(BASE_URI)) {};
        store.delete("delete.txt");
    }

    /**
     * Test get method.
     */
    @Test
    void testGet() {
        GitRepositoryKeyValueStore<String, byte[]> store = new GitRepositoryKeyValueStore<>(
                URI.create(BASE_URI)) {};
        assertNull(store.get("get.txt"));
    }

    /**
     * Test put method.
     */
    @Test
    void testPut() {
        GitRepositoryKeyValueStore<String, byte[]> store = new GitRepositoryKeyValueStore<>(
                URI.create(BASE_URI)) {};
        store.put("put.txt", "test".getBytes());
        assertNotNull(store.get("put.txt"));
    }
}
