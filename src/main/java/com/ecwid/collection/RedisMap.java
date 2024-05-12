package com.ecwid.collection;

import redis.clients.jedis.JedisCluster;

import java.util.AbstractMap;
import java.util.Set;

public class RedisMap<K, V> extends AbstractMap<K, V> {
    private final JedisCluster jedisCluster;

    public RedisMap(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    @Override
    public V get(Object key) {
        if (key instanceof String) {
            String value = jedisCluster.get((String) key);
            return value != null ? (V) Integer.valueOf(value) : null;
        }
        throw new UnsupportedOperationException("Key must be String and Value must be Integer.");
    }

    @Override
    public V put(K key, V value) {
        if (key instanceof String && value instanceof Integer) {
            jedisCluster.set((String) key, value.toString());
            return value;
        }
        throw new UnsupportedOperationException("Key must be String and Value must be Integer.");
    }

    @Override
    public V remove(Object key) {
        if (key instanceof String) {
            V value = get(key);
            jedisCluster.del((String) key);
            return value;
        }
        throw new UnsupportedOperationException("Key must be String and Value must be Integer.");
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof String && jedisCluster.exists((String) key);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("Operation not supported");
    }
}
