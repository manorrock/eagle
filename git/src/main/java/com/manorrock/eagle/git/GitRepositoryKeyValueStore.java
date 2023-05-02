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
package com.manorrock.eagle.git;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Comparator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import com.manorrock.eagle.api.KeyValueStore;
import java.util.Map;

/**
 * The Git repository KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the jey type.
 * @param <V> the value type.
 */
public class GitRepositoryKeyValueStore<K, V> implements KeyValueStore<K, V, String, byte[]> {

    /**
     * Stores the URI.
     */
    private final URI uri;

    /**
     * Constructor.
     *
     * @param uri the URI.
     */
    public GitRepositoryKeyValueStore(URI uri) {
        this.uri = uri;
    }

    @Override
    public void delete(K key) {
        String path = toUnderlyingKey(key);
        gitRepoDelete(path);
    }

    @Override
    public V get(K key) {
        String path = toUnderlyingKey(key);
        return (V) toValue(gitRepoGet(path));
    }

    @Override
    public Map<String, Object> getDelegate() {
        return Map.of("uri", uri);
    }

    @Override
    public void put(K key, V value) {
        String path = toUnderlyingKey(key);
        byte[] content = toUnderlyingValue(value);
        gitRepoPush(path, content);
    }

    @Override
    public String toUnderlyingKey(K key) {
        return key.toString();
    }

    @Override
    public byte[] toUnderlyingValue(V value) {
        return (byte[]) value;
    }

    /**
     * Delete the content from the given path.
     *
     * @param path the path.
     */
    private void gitRepoDelete(String path) {
        try {
            File directory = File.createTempFile("eagle", "git");
            directory.delete();
            directory.mkdirs();
            try (Git git = Git.cloneRepository()
                    .setURI(uri.toString())
                    .setDirectory(directory)
                    .setDepth(1)
                    .call()) {
                Repository repository = git.getRepository();
                File file = new File(repository.getDirectory().getParent(), path);
                file.delete();
                git.add()
                        .addFilepattern(path)
                        .call();
                git.commit()
                        .setMessage("Deleting " + path)
                        .call();
                git.push().call();
            }
            Files.walk(directory.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the content at the given path.
     *
     * @param path the path.
     * @return the content, or null if not found or HTTP status code other than
     * 200.
     */
    private byte[] gitRepoGet(String path) {
        byte[] result = null;
        try {
            File directory = File.createTempFile("eagle", "git");
            directory.delete();
            directory.mkdirs();
            try (Git git = Git.cloneRepository()
                    .setURI(uri.toString())
                    .setDirectory(directory)
                    .setDepth(1)
                    .call()) {
                Repository repository = git.getRepository();
                File file = new File(repository.getDirectory().getParent(), path);
                if (file.exists()) {
                    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                    Files.copy(file.toPath(), byteOutput);
                    result = byteOutput.toByteArray();
                }
            }
            Files.walk(directory.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Push the content to the given path.
     *
     * @param path the path.
     * @param content the content.
     */
    private void gitRepoPush(String path, byte[] content) {
        try {
            File directory = File.createTempFile("eagle", "git");
            directory.delete();
            directory.mkdirs();
            try (Git git = Git.cloneRepository()
                    .setURI(uri.toString())
                    .setDirectory(directory)
                    .setDepth(1)
                    .call()) {
                try (Repository repository = git.getRepository()) {
                    File file = new File(repository.getDirectory().getParent(), path);
                    file.createNewFile();
                    Files.copy(new ByteArrayInputStream(content), file.toPath(), REPLACE_EXISTING);
                    git.add()
                            .addFilepattern(path)
                            .call();
                    git.commit()
                            .setMessage("Putting " + path)
                            .call();
                }
                git.push().call();
            }
            Files.walk(directory.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
