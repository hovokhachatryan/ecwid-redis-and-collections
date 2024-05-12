package com.ecwid.map;

import com.ecwid.collection.RedisMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.JedisCluster;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedisMapTest {
    private JedisCluster jedisClusterMock;
    private RedisMap<String, Integer> redisMap;

    @BeforeEach
    public void setUp() {
        jedisClusterMock = Mockito.mock(JedisCluster.class);
        redisMap = new RedisMap<>(jedisClusterMock);
    }

    @Test
    public void testPut() {
        redisMap.put("key1", 1);
        verify(jedisClusterMock).set("key1", "1");
    }

    @Test
    public void testGet() {
        when(jedisClusterMock.get("key1")).thenReturn("1");
        Integer value = redisMap.get("key1");
        assertEquals(1, value);
    }

    @Test
    public void testRemove() {
        when(jedisClusterMock.get("key1")).thenReturn("1");
        Integer value = redisMap.remove("key1");
        verify(jedisClusterMock).del("key1");
        assertEquals(1, value);
    }

    @Test
    public void testContainsKey() {
        when(jedisClusterMock.exists("key1")).thenReturn(true);
        boolean exists = redisMap.containsKey("key1");
        assertTrue(exists);
    }
}
