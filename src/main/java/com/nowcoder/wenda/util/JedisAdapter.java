package com.nowcoder.wenda.util;

import com.nowcoder.wenda.controller.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by ${ywj} on 2017/12/27.
 */
@Service
public class JedisAdapter implements InitializingBean{
    private JedisPool pool;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);


    public static void print(int index,Object obj) {
        System.out.println(String.format("%d,%s", index, obj.toString()));
    }

    public static void main(String[] args) {

        /*Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

      //  jedis.set("hello", "world");
       // print(1, jedis.get("hello"));
      //  jedis.rename("hello", "newhello");
      //  print(1, jedis.get("newhello"));
        //带有过期时间，可以适用于验证码
      //  jedis.setex("hello2", 15, "world");
*//*
        jedis.set("pv", "100");
        jedis.incr("pv");
        print(2, jedis.get("pv"));
        jedis.incrBy("pv", 5);
        print(2, jedis.get("pv"));
        jedis.decrBy("pv", 2);
        print(2, jedis.get("pv"));

        print(3, jedis.keys("*"));*//*

        String listName = "list";
        *//*jedis.del(listName);
        for (int i=0;i<10;++i) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(4, jedis.lrange(listName, 0, 12));
        print(4, jedis.lrange(listName, 0, 3));
        print(5, jedis.llen("list"));
        print(6, jedis.lpop(listName));
        print(7, jedis.llen("list"));*//*
        print(10,jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "zz"));
        print(10,jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "qzz"));
        print(11, jedis.lrange(listName, 0, 12));

        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "18156537287");
        print(12, jedis.hget(userKey, "name"));
        print(13, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(14, jedis.hgetAll(userKey));
        print(15, jedis.hexists(userKey, "email"));
        print(16, jedis.hexists(userKey, "age"));
        print(17, jedis.hkeys(userKey));
        print(18, jedis.hvals(userKey));


        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";
        for(int i=0;i<10;++i) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * i));
        }
        print(20, jedis.smembers(likeKey1));
        print(21, jedis.smembers(likeKey2));
        print(22, jedis.sunion(likeKey1, likeKey2));
        print(23, jedis.sdiff(likeKey1, likeKey2));
        print(24, jedis.sinter(likeKey1, likeKey2));
        print(25, jedis.sismember(likeKey1, "11"));
        //删除，srem,lrem
        jedis.srem(likeKey1, "1");
        print(27, jedis.smembers(likeKey1));
        jedis.smove(likeKey2, likeKey1, "25");
        print(28, jedis.smembers(likeKey1));
        print(29, jedis.scard(likeKey1));

        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "jim");
        jedis.zadd(rankKey, 25, "sam");
        jedis.zadd(rankKey, 5, "tom");
        jedis.zadd(rankKey, 45, "meimei");
        jedis.zadd(rankKey, 65, "lucy");
        jedis.zadd(rankKey, 95, "nancy");
        print(30, jedis.zcard(rankKey));
        print(31,jedis.zcount(rankKey,61,100));
        print(32, jedis.zscore(rankKey, "lucy"));
        jedis.zincrby(rankKey, 2, "lucy");
        print(33, jedis.zscore(rankKey, "lucy"));
        jedis.zincrby(rankKey, 2, "luc");
        print(34, jedis.zscore(rankKey, "luc"));
        print(35,jedis.zrange(rankKey,0,100));
        //求出前十位，可用于排行榜
        print(36,jedis.zrange(rankKey,0,3));
        print(37,jedis.zrevrange(rankKey,0,3));
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey,"4" +
                "0", "100")) {
            print(37,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }
        print(38,jedis.zrank(rankKey,"meimei"));
        print(39,jedis.zrevrank(rankKey,"meimei"));

        String setKey = "zset";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");
        print(40,jedis.zlexcount(setKey,"-","+"));
        print(41,jedis.zlexcount(setKey,"(b","[d"));
        print(41,jedis.zlexcount(setKey,"[b","[d"));
        //删除
        jedis.zrem(setKey, "b");
        print(43, jedis.zrange(setKey, 0, 10));


        JedisPool pool = new JedisPool();


        Jedis jedis = new Jedis("redis://localhost:6379/10");
        jedis.flushDB();
        jedis.sadd("2", "48");
*/

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool(("redis://localhost:6379/10"));
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public Jedis getJedis(){
        return pool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }

    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (tx != null) {
                try {
                    tx.close();
                } catch (IOException ioe) {
                    logger.error("发生异常" + ioe.getMessage());
                }

            }
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key, double score, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zadd(key, score,value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
    public long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
