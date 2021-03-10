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
package com.manorrock.eagle.filesystem;

import com.manorrock.eagle.api.KeyValueStore;
import com.manorrock.eagle.api.KeyValueStoreMapper;
import com.manorrock.eagle.common.FilenameToFileMapper;
import com.manorrock.eagle.common.StringToByteArrayMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;

/**
 * A file-system based KeyValueStore.
 * 
 * <p>
 *  Note the default keyMapper is setup assuming the K type is String, the 
 *  default valueMapper is setup assuming the V type is String. If that is not
 *  the case make sure to deliver the appropriate mapper.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public class FilesystemKeyValueStore<K, V> implements KeyValueStore<K, V> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(FilesystemKeyValueStore.class.getPackage().getName());

    /**
     * Stores the key mapper.
     */
    private KeyValueStoreMapper keyMapper;

    /**
     * Stores the value mapper.
     */
    private KeyValueStoreMapper valueMapper;

    /**
     * Constructor.
     *
     * @param baseDirectory the base directory.
     */
    public FilesystemKeyValueStore(File baseDirectory) {
        this.keyMapper = new FilenameToFileMapper(baseDirectory);
        this.valueMapper = new StringToByteArrayMapper();
    }

    @Override
    public void delete(K key) {
        File file = (File) keyMapper.to(key);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public V get(K key) {
        V result = null;
        File file = (File) keyMapper.to(key);
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
                result = (V) valueMapper.from(bytes);
            } catch (IOException ioe) {
                LOGGER.log(WARNING, "Unable to get content for key: " + key, ioe);
            }
        }
        return result;
    }

    @Override
    public void put(K key, V value) {
        File file = (File) keyMapper.to(key);
        File parentFile = file.getParentFile();
        parentFile.mkdirs();
        try ( FileOutputStream fileOutput = new FileOutputStream(file)) {
            fileOutput.write((byte[]) valueMapper.to(value));
            fileOutput.flush();
        } catch (IOException ioe) {
            LOGGER.log(WARNING, "Unable to put content for key: " + key, ioe);
        }
    }

    @Override
    public void setKeyMapper(KeyValueStoreMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public void setValueMapper(KeyValueStoreMapper valueMapper) {
        this.valueMapper = valueMapper;
    }
}
