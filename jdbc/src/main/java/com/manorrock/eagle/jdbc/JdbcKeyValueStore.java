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

import com.manorrock.eagle.api.KeyValueStoreException;
import com.manorrock.eagle.api.KeyValueStore2;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The JDBC KeyValueStore.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <K> the key type.
 * @param <V> the value type.
 */
public abstract class JdbcKeyValueStore<K, V> implements KeyValueStore2<K, V, Long, byte[]> {

    /**
     * Stores the connection.
     */
    private Connection connection;

    /**
     * Constructor.
     *
     * @param uri the URI.
     */
    public JdbcKeyValueStore(URI uri) {
        try {
            connection = DriverManager.getConnection(uri.toString());
        } catch (SQLException se) {
            throw new KeyValueStoreException(se);
        }
    }

    @Override
    public void delete(K key) {
        try {
            Long id = toUnderlyingKey(key);
            jdbcDelete(id);
        } catch(SQLException se) {
            throw new KeyValueStoreException(se);
        }
    }

    @Override
    public V get(K key) {
        try {
            Long id = toUnderlyingKey(key);
            return toValue(jdbcSelect(id));
        } catch(SQLException se) {
            throw new KeyValueStoreException(se);
        }
    }

    @Override
    public void put(K key, V value) {
        try {
            Long id = toUnderlyingKey(key);
            byte[] jdbcValue = jdbcSelect(id);
            if (jdbcValue == null) {
                jdbcInsert(id, toUnderlyingValue(value));
            } else {
                jdbcUpdate(id, toUnderlyingValue(value));
            }
        } catch(SQLException se) {
            throw new KeyValueStoreException(se);
        }
    }

    @Override
    public K toKey(Long underlyingKey) {
        return (K) underlyingKey;
    }

    @Override
    public Long toUnderlyingKey(K key) {
        return (Long) key;
    }

    @Override
    public byte[] toUnderlyingValue(V value) {
        return (byte[]) value;
    }

    @Override
    public V toValue(byte[] underlyingValue) {
        return (V) underlyingValue;
    }

    private void jdbcDelete(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM kvs AS o WHERE o.kvs_id = ?");
        statement.setLong(1, id);
        statement.execute();
    }

    private void jdbcInsert(Long id, byte[] bytes) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO kvs(kvs_id, kvs_value) VALUES(?,?)");
        statement.setLong(1, id);
        statement.setBytes(2, bytes);
        statement.execute();
    }

    private byte[] jdbcSelect(Long id) throws SQLException {
        byte[] result = null;
        PreparedStatement statement = connection.prepareStatement(
                "SELECT kvs_value FROM kvs AS o WHERE o.kvs_id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = resultSet.getBytes(1);
        }
        return result;
    }

    private void jdbcUpdate(Long id, byte[] bytes) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE kvs SET kvs_value = ? WHERE kvs_id = ?");
        statement.setBytes(1, bytes);
        statement.setLong(2, id);
        statement.execute();
    }
}
