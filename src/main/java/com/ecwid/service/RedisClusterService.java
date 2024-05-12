package com.ecwid.service;

import com.ecwid.model.RedisNode;

import java.util.Set;

/**
 * Generic interface for get/pur/remove operations in Redis and cluster management.
 *
 * @param <K> The type of keys maintained by this Redis service
 * @param <V> The type of mapped values
 */
public interface RedisClusterService<K, V> {

    /**
     * Gets the value associated with the specified key.
     */
    V get(K key);

    /**
     * Sets the value associated with the specified key.
     */
    V put(K key, V value);

    /**
     * Removes the value associated with the specified key.
     */
    V remove(K key);

    /**
     * Checks if the Redis cluster contains a value for the specified key.
     */
    boolean containsKey(K key);

    /**
     * Adds a new node to the Redis cluster.
     */
    void addNode(RedisNode newNode);

    /**
     * Removes a node from the Redis cluster.
     */
    void removeNode(RedisNode nodeToRemove);

    /**
     * Retrieves the set of nodes currently in the Redis cluster.
     */
    Set<RedisNode> getNodes();

}