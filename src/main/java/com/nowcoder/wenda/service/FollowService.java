package com.nowcoder.wenda.service;

import com.nowcoder.wenda.util.JedisAdapter;
import com.nowcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ${ywj} on 2018/1/3.
 */
@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean follow(int userId, int entityType, int entityId) {
        //关注列表的key
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        //关注粉丝的key
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    public boolean unfollow(int userId, int entityType, int entityId) {
        //关注列表的key
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        //关注粉丝的key
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zrem(followerKey, String.valueOf(userId));
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    //help函数，把String转成Integer
    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));

    }
    //offset翻页
    public List<Integer> getFollowers(int entityType, int entityId,int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset, count));

    }

    public List<Integer> getFollowees(int entityType, int entityId, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));

    }
    //offset翻页
    public List<Integer> getFollowees(int entityType, int entityId,int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, count));

    }

    //判断有多少个粉丝
    public long getFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return jedisAdapter.zcard(followeeKey);
    }
//判断是否关注了
    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }
}
