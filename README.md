# Redis Cluster Setup and Management

This project demonstrates how to set up a Redis cluster with three nodes and add a fourth node using Java code.

The project uses Jedis, JUnit, and Mockito for Redis operations and testing.

## Prerequisites

- Redis
- Java 17
- Maven

## Project Structure

The Redis node configurations are located in the `resources/redis/nodes` directory with the following structure:
- `7000-node/redis.conf`
- `7001-node/redis.conf`
- `7002-node/redis.conf`
- `7003-node/redis.conf`

## Step-by-Step Guide

### Step 1: Install Redis

If you don't have Redis installed, you can download and install it from [here](https://redis.io/download).

### Step 2: Run Redis Nodes

Run three Redis nodes using the provided configuration files:

```bash
redis-server resources/redis/nodes/7000-node/redis.conf
redis-server resources/redis/nodes/7001-node/redis.conf
redis-server resources/redis/nodes/7002-node/redis.conf
redis-server resources/redis/nodes/7003-node/redis.conf
```

### Step 3: Create Redis Cluster

Create the Redis cluster with three nodes using redis-cli:

```bash
redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 --cluster-replicas 0
```