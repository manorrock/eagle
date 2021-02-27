/*
 *  Copyright (c) 2002-2021, Manorrock.com. All Rights Reserved.
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
package com.manorrock.eagle.factory;

import com.manorrock.eagle.api.KeyValueStore;
import com.manorrock.eagle.redis.RedisKeyValueStore;
import io.lettuce.core.RedisURI;
import java.net.URI;
import java.util.Map;

/**
 * A KeyValueStore factory.
 *
 * <p>
 * This class delivers you with a KeyValueStore factory that takes a
 * configuration map anb produces a KeyValueStore instance.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public final class KeyValueStoreFactory {
    
    /**
     * Constructor.
     */
    private KeyValueStoreFactory() {
    }

    /**
     * Get the KeyValueStore.
     *
     * @param configuration the configuration map.
     * @return the KeyValueStore, or null if unable to create one.
     */
    public static KeyValueStore getKeyValueStore(Map configuration) {
        KeyValueStore result = null;
        String className = (String) configuration.get("className");
        if (className != null) {
            switch (className) {
                case "com.manorrock.eagle.redis.RedisKeyValueStore":
                    result = getRedisKeyValueStore(configuration);
                    break;
            }
        }
        return result;
    }

    /**
     * Create the RedisKeyValueStore.
     *
     * @param configuration the configuration.
     * @return the KeyValueStore or null if it could not be created.
     */
    private static KeyValueStore getRedisKeyValueStore(Map configuration) {
        URI uri = RedisURI.Builder
                .redis((String) configuration.get("hostname"))
                .withPort(configuration.containsKey("portNumber")
                        ? (Integer) configuration.get("portNumber") : 6379)
                .withSsl(configuration.containsKey("ssl")
                        ? (Boolean) configuration.get("ssl") : false)
                .withPassword((String) configuration.get("password"))
                .build()
                .toURI();
        return new RedisKeyValueStore(uri);
    }
}