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
package com.manorrock.eagle.azure.cosmos;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The JUnit tests for the AzureBlobKeyValueStore class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class CosmosKeyValueStoreTest {

    /**
     * Stores the KV store.
     */
    private static CosmosKeyValueStore<CosmosKey, byte[]> store;

    /**
     * Setup before testing.
     */
    @BeforeAll
    public static void setUpClass() {
        store = new CosmosKeyValueStore<>(
                System.getProperty("cosmos.endpoint"),
                System.getProperty("cosmos.masterKey"),
                System.getProperty("cosmos.consistencyLevel"),
                System.getProperty("cosmos.databaseName"),
                System.getProperty("cosmos.containerName"));
    }

    /**
     * Tear down after testing.
     */
    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Test delete method.
     */
    @Test
    public void testDelete() {
        CosmosKey key = new CosmosKey();
        key.setItemId("delete");
        store.put(key, "value".getBytes());
        assertEquals("value", new String(store.get(key)));
        store.delete(key);
        assertNull(store.get(key));
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        CosmosKey key = new CosmosKey();
        key.setItemId("get");
        store.put(key, "value".getBytes());
        assertEquals("value", new String(store.get(key)));
        store.delete(key);
    }

    /**
     * Test put method.
     */
    @Test
    public void testPut() {
        CosmosKey key = new CosmosKey();
        key.setItemId("put");
        store.put(key, "value".getBytes());
        assertEquals("value", new String(store.get(key)));
        store.delete(key);
    }
}
