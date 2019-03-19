package cn.yan.study.utils.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by gentlemen_yan on 2019/3/2.
 */
public class RedisUtils {
    private static ShardedJedisPool pool;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("118.24.150.85", 6379);
        jedisShardInfo1.setPassword("redisywk");
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        pool = new ShardedJedisPool(config, list);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        String keys = "myname";
        String vaule = jedis.set(keys, "lxr");
        System.out.println(vaule);

        System.out.println("====key功能展示====");
        try {
//            jedis.select(0);
//            System.out.println("清除数据：" + jedis.flushDB());
            System.out.println("判断某个键是否存在：" + jedis.exists("1"));
            System.out.println("新增{1，a}键值对:" + jedis.set("1", "a"));
            System.out.println(jedis.exists("1"));
            System.out.println("新增{2，b}键值对:" + jedis.set("2", "b"));
//            System.out.println("系统中所有的键如下：" + jedis.keys("*").toString());
            System.out.println("删除键 1:" + jedis.del("1"));
            System.out.println("判断键 1是否存在：" + jedis.exists("1"));
            System.out.println("设置键 2的过期时间为5s:" + jedis.expire("2", 5));
            TimeUnit.SECONDS.sleep(2);
            System.out.println("查看键 2的剩余生存时间：" + jedis.ttl("2"));
            System.out.println("移除键 2的生存时间：" + jedis.persist("2"));
            System.out.println("查看键 2的剩余生存时间：" + jedis.ttl("2"));
            System.out.println("查看键 2所存储的值的类型：" + jedis.type("2"));
            System.out.println("查看键 2的值：" + jedis.get("2"));

            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}