package com.ecwid;

import com.ecwid.model.RedisClusterConfiguration;
import com.ecwid.model.RedisNode;
import com.ecwid.service.impl.RedisClusterServiceImpl;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        RedisNode node1 = new RedisNode("127.0.0.1", 7000);
        RedisNode node2 = new RedisNode("127.0.0.1", 7001);
        RedisNode node3 = new RedisNode("127.0.0.1", 7002);
        RedisNode node4 = new RedisNode("127.0.0.1", 7003);

        HashSet<RedisNode> nodes = new HashSet<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        RedisClusterServiceImpl<String, Integer> redisClusterServiceImpl = new RedisClusterServiceImpl<>(new RedisClusterConfiguration(nodes));

        // Demonstration of put, get, and containsKey methods
        redisClusterServiceImpl.put("key1", 1);
        redisClusterServiceImpl.put("key2", 2);

        System.out.println("key1: " + redisClusterServiceImpl.get("key1")); // Output: key1: 1
        System.out.println("key2: " + redisClusterServiceImpl.get("key2")); // Output: key2: 2
        System.out.println("Contains key1: " + redisClusterServiceImpl.containsKey("key1")); // Output: Contains key1: true

        // Demonstration of remove method
        redisClusterServiceImpl.remove("key1");
        System.out.println("key1 after removal: " + redisClusterServiceImpl.get("key1")); // Output: key1 after removal: null

        // Demonstration of addNode method
        redisClusterServiceImpl.addNode(node4);

        // Demonstration of removeNode method
        redisClusterServiceImpl.removeNode(node2);
    }
}
