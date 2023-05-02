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
package com.manorrock.eagle.calico;

import java.net.URI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The JUnit tests for the CalicoKeyValueStore class.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class CalicoKeyValueStoreTest {
    
    /**
     * Stores the base URI.
     */
    private static final String BASE_UR = "http://localhost:8080/calico/api/";

    /**
     * Test delete method.
     */
    @Test
    public void testDelete() {
        CalicoKeyValueStore<String, byte[]> store = new CalicoKeyValueStore<>(
                URI.create(BASE_UR));
        store.put("delete", "delete".getBytes());
        assertNotNull(store.get("delete"));
        store.delete("delete");
        assertNull(store.get("delete"));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        CalicoKeyValueStore<String, byte[]> store = new CalicoKeyValueStore<>(
                URI.create(BASE_UR));
        store.delete("get");
        assertNull(store.get("get"));
        store.put("get", "get".getBytes());
        assertNotNull(store.get("get"));
        store.delete("get");
    }

    /**
     * Test of put method, of class CalicoKeyValueStore.
     */
    @Test
    public void testPut() {
        CalicoKeyValueStore<String, byte[]> store = new CalicoKeyValueStore<>(
                URI.create(BASE_UR));
        store.put("put", "put".getBytes());
        assertNotNull(store.get("put"));
        store.delete("put");
    }
}
