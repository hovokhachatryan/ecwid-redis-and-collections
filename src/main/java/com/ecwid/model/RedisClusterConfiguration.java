package com.ecwid.model;

import java.util.Set;

/**
 * Represents the configuration for a Redis cluster.
 *
 * It can also be configured as a bean for different environments when Spring Framework is used.
 *
 * @param nodes The set of Redis nodes {@link RedisNode} class.
 */
public record RedisClusterConfiguration(Set<RedisNode> nodes) {
}
