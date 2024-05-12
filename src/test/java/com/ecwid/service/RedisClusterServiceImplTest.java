package com.ecwid.service;

import com.ecwid.collection.RedisMap;
import com.ecwid.model.RedisClusterConfiguration;
import com.ecwid.model.RedisNode;
import com.ecwid.service.impl.RedisClusterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RedisClusterServiceImplTest {
    private JedisCluster jedisClusterMock;
    private RedisMap<String, Integer> redisMapMock;
    private RedisClusterServiceImpl<String, Integer> redisClusterServiceImpl;

    @BeforeEach
    public void setUp() throws Exception {
        Set<RedisNode> clusterNodes = new HashSet<>();
        clusterNodes.add(new RedisNode("127.0.0.1", 7000));
        clusterNodes.add(new RedisNode("127.0.0.1", 7001));
        clusterNodes.add(new RedisNode("127.0.0.1", 7002));

        jedisClusterMock = Mockito.mock(JedisCluster.class);
        redisMapMock = Mockito.mock(RedisMap.class);

        redisClusterServiceImpl = new RedisClusterServiceImpl<>(new RedisClusterConfiguration(clusterNodes));

        // Use reflection to inject mocks into RedisService
        Field jedisClusterField = RedisClusterServiceImpl.class.getDeclaredField("jedisCluster");
        jedisClusterField.setAccessible(true);
        jedisClusterField.set(redisClusterServiceImpl, jedisClusterMock);

        Field redisMapField = RedisClusterServiceImpl.class.getDeclaredField("redisMap");
        redisMapField.setAccessible(true);
        redisMapField.set(redisClusterServiceImpl, redisMapMock);
    }

    @Test
    public void testPut() {
        redisClusterServiceImpl.put("key1", 1);
        verify(redisMapMock).put("key1", 1);
    }

    @Test
    public void testGet() {
        when(redisMapMock.get("key1")).thenReturn(1);
        Integer value = redisClusterServiceImpl.get("key1");
        assertEquals(1, value);
    }

    @Test
    public void testRemove() {
        when(redisMapMock.remove("key1")).thenReturn(1);
        Integer value = redisClusterServiceImpl.remove("key1");
        verify(redisMapMock).remove("key1");
        assertEquals(1, value);
    }

    @Test
    public void testContainsKey() {
        when(redisMapMock.containsKey("key1")).thenReturn(true);
        boolean exists = redisClusterServiceImpl.containsKey("key1");
        assertTrue(exists);
    }

    @Test
    public void testAddNode() {
        RedisNode newNode = new RedisNode("127.0.0.1", 7003);
        redisClusterServiceImpl.addNode(newNode);
        verify(jedisClusterMock, atLeastOnce()).close();
    }

    @Test
    public void testRemoveNode() {
        RedisNode nodeToRemove = new RedisNode("127.0.0.1", 7001);
        redisClusterServiceImpl.removeNode(nodeToRemove);
        verify(jedisClusterMock, atLeastOnce()).close();
    }
}
