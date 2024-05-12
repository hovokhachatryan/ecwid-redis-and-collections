package com.ecwid.service.impl;

import com.ecwid.collection.RedisMap;
import com.ecwid.model.RedisClusterConfiguration;
import com.ecwid.model.RedisNode;
import com.ecwid.service.RedisClusterService;
import redis.clients.jedis.HostAndPort;

import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisClusterServiceImpl<K, V> implements RedisClusterService<K, V> {
    private Set<RedisNode> nodes;
    private JedisCluster jedisCluster;
    private RedisMap<K, V> redisMap;

    public RedisClusterServiceImpl(RedisClusterConfiguration redisClusterConfiguration) {
        this.nodes = new HashSet<>(redisClusterConfiguration.nodes());
        this.jedisCluster = new JedisCluster(toHostAndPorts(this.nodes));
        this.redisMap = new RedisMap<>(this.jedisCluster);
    }


    @Override
    public V get(K key) {
        return redisMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return redisMap.put(key, value);
    }

    @Override
    public V remove(K key) {
        return redisMap.remove(key);
    }

    @Override
    public boolean containsKey(K key) {
        return redisMap.containsKey(key);
    }

    @Override
    public void addNode(RedisNode newNode) {
        this.nodes.add(newNode);
        reinitializeJedisCluster();
    }

    @Override
    public void removeNode(RedisNode nodeToRemove) {
        this.nodes.remove(nodeToRemove);
        reinitializeJedisCluster();
    }

    @Override
    public Set<RedisNode> getNodes() {
        return this.nodes;
    }

    // Private method to reinitialize the JedisCluster instance
    private void reinitializeJedisCluster() {
        try {
            this.jedisCluster.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.jedisCluster = new JedisCluster(toHostAndPorts(this.nodes));
        this.redisMap = new RedisMap<>(this.jedisCluster);
    }

    private Set<HostAndPort> toHostAndPorts(Set<RedisNode> nodes) {
        return nodes.stream()
                .map(redisNode -> new HostAndPort(redisNode.host(), redisNode.port()))
                .collect(Collectors.toSet());
    }

}
