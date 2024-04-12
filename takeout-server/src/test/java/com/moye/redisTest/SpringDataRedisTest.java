package com.moye.redisTest;

import com.moye.TakeoutApplication;
import com.moye.config.RedisConfiguration;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@SpringBootTest(classes = TakeoutApplication.class)  //springboot下测试环境注解
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        redisTemplate.opsForValue().set("name", "moye");
        System.out.println(redisTemplate.opsForValue().get("name"));

        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void testString() {
        //get set setex setnx
        redisTemplate.opsForValue().set("city", "北京");
        String city = (String) redisTemplate.opsForValue().get("city");
        System.out.println(city);

        //设置有效期
        redisTemplate.opsForValue().set("code", "1234", 1000, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("code"));

        //setnx
        Boolean flag = redisTemplate.opsForValue().setIfAbsent("lock", "1");
        System.out.println(flag);
        flag = redisTemplate.opsForValue().setIfAbsent("lock", "2");
        System.out.println("flag = " + flag);
    }

    @Test
    public void testHash() {
        //hset hget hdel hlen hkeys hvals hgetall
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("111", "name", "moye");
        hashOperations.put("111", "age", "20");
        String name = (String) hashOperations.get("111", "name");
        System.out.println("name = " + name);

        Set keys = hashOperations.keys("111");
        System.out.println("keys = " + keys);

        List values = hashOperations.values("111");
        System.out.println("values = " + values);

        hashOperations.delete("111", "age");
    }

    @Test
    public void testList() {
        //lpush  rpop llen lrange
        ListOperations listOperations = redisTemplate.opsForList();

        listOperations.leftPushAll("myList", "a", "b", "c", "d");
        listOperations.leftPush("myList", "e");

        listOperations.range("myList", 0, -1).forEach(System.out::println);

        String value = (String) listOperations.rightPop("myList");
        System.out.println("value = " + value);

        Long size = listOperations.size("myList");
        System.out.println("size = " + size);
    }

    @Test
    public void testSet() {
        //sadd spop smembers sismember scard sunion srem sinter
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("mySet", "a", "b", "c", "d", "e", "f");
        setOperations.add("mySet2", "a", "b", "c", "1", "2", "3");

        Set mySet = setOperations.members("mySet");
        System.out.println("mySet = " + mySet);

        Long size = setOperations.size("mySet");
        System.out.println("size = " + size);

        Set intersect = setOperations.intersect("mySet", "mySet2");
        System.out.println("intersect = " + intersect);

        Set union = setOperations.union("mySet", "mySet2");
        System.out.println("union = " + union);

        String value = (String) setOperations.pop("mySet");
        System.out.println("value = " + value);

        Boolean flag = setOperations.isMember("mySet", "a");
        System.out.println("flag = " + flag);
    }

    @Test
    public void testZSet() {
        //zadd zrange zincrby zrem
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("myZSet", "a", 1);
        zSetOperations.add("myZSet", "b", 12);
        zSetOperations.add("myZSet", "c", 9);

        zSetOperations.range("myZSet", 0, -1).forEach(System.out::println);
        Set myZSet = zSetOperations.range("myZSet", 0, -1);
        System.out.println("myZSet = " + myZSet);

        zSetOperations.incrementScore("myZSet", "a", 10);
        zSetOperations.range("myZSet", 0, -1).forEach(System.out::println);

        zSetOperations.remove("myZSet", "a");
        zSetOperations.range("myZSet", 0, -1).forEach(System.out::println);
    }

    @Test
    public void testCommon() {
        //keys exists type del
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);

        Boolean name = redisTemplate.hasKey("name");
        Boolean set1 = redisTemplate.hasKey("set1");

        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
        }

        redisTemplate.delete("mylist");
    }

}
