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

import com.manorrock.common.kvs.api.KeyValueMapper;
import java.io.File;

/**
 * The filename to file mapper.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
class FilenameToFileMapper implements KeyValueMapper<String, File> {

    /**
     * Stores the base directory.
     */
    private final File baseDirectory;
    
    /**
     * Constructor.
     * 
     * @param baseDirectory the base directory.
     */
    public FilenameToFileMapper(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }
    
    /**
     * Map the filename to a file.
     * 
     * @param filename the filename.
     * @return the file.
     */
    @Override
    public File to(String filename) {
        return new File(baseDirectory, filename);
    }

    /**
     * Map the file to a filename.
     * 
     * @param file the file.
     * @return the filename.
     */
    @Override
    public String from(File file) {
        String filename = file.getAbsolutePath();
        filename = filename.substring(baseDirectory.getAbsolutePath().length());
        return filename;
    }
}
