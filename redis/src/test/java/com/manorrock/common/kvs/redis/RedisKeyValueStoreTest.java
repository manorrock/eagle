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
package com.manorrock.common.kvs.redis;

import io.lettuce.core.RedisURI;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the RedisKeyValueStore class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class RedisKeyValueStoreTest {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(RedisKeyValueStoreTest.class.getPackage().getName());

    /**
     * Stores the URI.
     */
    private static URI uri;

    /**
     * Verify if we can test Redis.
     */
    @BeforeAll
    public static void setUpClass() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("redis.properties"));
            uri = RedisURI.Builder.redis(properties.getProperty("host"))
                    .withPort(Integer.parseInt(properties.getProperty("port")))
                    .withSsl(false)
                    .withPassword(properties.getProperty("password")).build().toURI();
        } catch (IOException ioe) {
            LOGGER.log(WARNING, "An exception occurred", ioe);
        }
    }

    /**
     * Test delete method.
     */
    @Test
    public void testDelete() {
        if (uri != null) {
            RedisKeyValueStore<String, byte[]> kvs = new RedisKeyValueStore<>(uri);
            kvs.put("delete", "deleteme".getBytes());
            assertEquals("deleteme", new String(kvs.get("delete")));
            kvs.delete("delete");
            assertNull(kvs.get("delete"));
        }
    }
}
