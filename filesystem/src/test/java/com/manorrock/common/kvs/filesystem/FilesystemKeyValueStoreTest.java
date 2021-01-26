/*
 *  Copyright (c) 2002-2020, Manorrock.com. All Rights Reserved.
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
package com.manorrock.common.kvs.filesystem;

import com.manorrock.common.kvs.common.StringToByteArrayMapper;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the FilesystemKeyValueStore class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class FilesystemKeyValueStoreTest {

    /**
     * Test delete method.
     */
    @Test
    public void testDelete() {
        FilesystemKeyValueStore<String, byte[]> kvs
                = new FilesystemKeyValueStore<>(new File("target"));
        kvs.put("delete", "deleteme".getBytes());
        assertTrue(new File("target/delete").exists());
        kvs.delete("delete");
        assertFalse(new File("target/delete").exists());
    }
    
    /**
     * Test delete method.
     */
    @Test
    public void testDelete2() {
        FilesystemKeyValueStore<String, String> kvs
                = new FilesystemKeyValueStore<>(new File("target"));
        kvs.setValueMapper(new StringToByteArrayMapper());
        kvs.put("delete", "deleteme");
        assertTrue(new File("target/delete").exists());
        kvs.delete("delete");
        assertFalse(new File("target/delete").exists());
    }
    
    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        FilesystemKeyValueStore<String, byte[]> kvs
                = new FilesystemKeyValueStore<>(new File("target"));
        kvs.put("get", "getme".getBytes());
        assertTrue(new File("target/get").exists());
        assertEquals("getme", new String(kvs.get("get")));
    }
    
    /**
     * Test get method.
     */
    @Test
    public void testGet2() {
        FilesystemKeyValueStore<String, String> kvs
                = new FilesystemKeyValueStore<>(new File("target"));
        kvs.setValueMapper(new StringToByteArrayMapper());
        kvs.put("get", "getme");
        assertTrue(new File("target/get").exists());
        assertEquals("getme", new String(kvs.get("get")));
    }

    /**
     * Test put method.
     */
    @Test
    public void testPut() {
        FilesystemKeyValueStore<String, byte[]> kvs
                = new FilesystemKeyValueStore<>(new File("target"));
        kvs.put("put", "putme".getBytes());
        assertTrue(new File("target/put").exists());
        assertEquals("putme", new String(kvs.get("put")));
    }
}
