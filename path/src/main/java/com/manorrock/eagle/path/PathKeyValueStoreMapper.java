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
package com.manorrock.eagle.path;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import com.manorrock.eagle.api.KeyValueStoreMapper;

/**
 * The default Filesystem KeyValueStoreMapper.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class PathKeyValueStoreMapper implements KeyValueStoreMapper<String, String> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PathKeyValueStoreMapper.class.getName());

    /**
     * Stores the base path.
     */
    private final Path basePath;

    /**
     * Constructor.
     *
     * @param basePath the base path.
     */
    public PathKeyValueStoreMapper(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public Object fromKey(String key) {
        return basePath.resolve(key);
    }

    @Override
    public Object fromValue(String value) {
        byte[] result = null;
        try {
            result = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            LOGGER.log(WARNING, "Encountered an unsupported encoding", uee);
        }
        return result;
    }
    
    @Override
    public String toKey(Object underlyingKey) {
        return ((Path) underlyingKey).toString().substring(basePath.toString().length());
    }

    @Override
    public String toValue(Object underlyingValue) {
        String result = null;
        try {
            result = new String((byte[]) underlyingValue, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            LOGGER.log(WARNING, "Encountered an unsupported encoding", uee);
        }
        return result;
    }
    
}
