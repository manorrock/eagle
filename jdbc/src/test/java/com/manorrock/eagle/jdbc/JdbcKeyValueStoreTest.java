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
package com.manorrock.eagle.jdbc;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the JdbcKeyValueStore class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class JdbcKeyValueStoreTest {

    /**
     * Stores the JDBC URI used for testing.
     */
    private static String JDBC_URI = "jdbc:h2:mem:test";

    /**
     * Before all.
     */
    @BeforeAll
    public static void beforeAll() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URI);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS kvs(kvs_id BIGINT PRIMARY KEY, kvs_value BLOB(10K));");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * Test delete method.
     */
    @Test
    public void testDelete() {
        JdbcKeyValueStore<Long, byte[]> store = new JdbcKeyValueStore<>(
                URI.create(JDBC_URI), "kvs", "kvs_id", "kvs_value") {
        };
        store.delete(1234L);
    }

    /**
     * Test get method.
     */
    @Test
    public void testGet() {
        JdbcKeyValueStore<Long, byte[]> store = new JdbcKeyValueStore<>(
                URI.create(JDBC_URI), "kvs", "kvs_id", "kvs_value") {
        };
        assertNull(store.get(2345L));
    }

    /**
     * Test put method.
     */
    @Test
    public void testPut() {
        JdbcKeyValueStore<Long, byte[]> store = new JdbcKeyValueStore<>(
                URI.create(JDBC_URI), "kvs", "kvs_id", "kvs_value") {
        };
        store.put(3456L, "test".getBytes());
        assertNotNull(store.get(3456L));
        store.put(3456L, "test2".getBytes());
        assertEquals("test2", new String((byte[]) store.get(3456L)));
    }
}
