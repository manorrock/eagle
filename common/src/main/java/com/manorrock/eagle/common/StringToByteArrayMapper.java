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
package com.manorrock.eagle.common;

import com.manorrock.eagle.api.KeyValueStore;
import com.manorrock.eagle.api.KeyValueStoreMapper;
import java.io.UnsupportedEncodingException;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;

/**
 * The string to byte-array mapper.
 * 
 * <p>
 *  This mapper will convert from string to byte-array and back.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class StringToByteArrayMapper implements KeyValueStoreMapper<String, byte[]> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(KeyValueStore.class.getPackage().getName());

    @Override
    public byte[] to(String from) {
        byte[] result = null;
        try {
            result = from.getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            LOGGER.log(WARNING, "Encountered an unsupported encoding", uee);
        }
        return result;
    }

    @Override
    public String from(byte[] from) {
        String result = null;
        try {
            result = new String(from, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            LOGGER.log(WARNING, "Encountered an unsupported encoding", uee);
        }
        return result;
    }
}
