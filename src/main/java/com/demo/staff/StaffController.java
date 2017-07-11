package com.demo.staff;

import java.util.*;

import base.redis.SerializeUtil;
import base.redis.ShardedJedisContainer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import base.utils.jsonresult.XJsonResult;
import base.utils.jsonresult.XJsonResultFactory;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 员工管理
 */
@Controller
@RequestMapping("/admin/StaffCtrl/")
public class StaffController {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    protected ShardedJedisPool shardedJedisPool;

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    ShardedJedis shardedJedis;

    ShardedJedisContainer shardedJedisContainer;

    /**
     * 查询
     */
    @RequestMapping(value = "search")
    public @ResponseBody XJsonResult search(HttpServletRequest request, HttpServletResponse response, @RequestBody StaffEntry staffEty) throws Exception {

        int count;
        List<StaffEntry> list;

        try {
            shardedJedis = shardedJedisPool.getResource();
            // String abc = shardedJedis.get("name");
            // shardedJedis.sadd("name", "test");

            // StringOperate();
            // ListOperate();
            // SetOperate();
            // SortedSetOperate();
            // HashOperate();

            byte[] resultList = null;
            byte[] resultInteger = null;
            // resultInteger = shardedJedis.get(SerializeUtil.serialize("edf"));
            // resultList = shardedJedis.get(SerializeUtil.serialize("abc"));

            if (resultList != null) {
                list = (List<StaffEntry>) SerializeUtil.unserialize(resultList);
                count = ((Integer) SerializeUtil.unserialize(resultInteger)).intValue();
            } else {
                count = staffMapper.selectLimitCount(staffEty);
                list = staffMapper.selectByLimit(staffEty);

                SqlSession session = sqlSessionFactory.openSession();
                try {
                    StaffMapper mapper = session.getMapper(StaffMapper.class);
                    count = mapper.selectLimitCount(staffEty);
                    list = mapper.selectByLimit(staffEty);
                } finally {
                    session.clearCache();
                    session.close();
                }

                /*
                 * shardedJedisContainer.getReadWriteLock().writeLock().lock();
                 * 
                 * resultList = SerializeUtil.serialize(list); shardedJedis.set(SerializeUtil.serialize("abc"), resultList);
                 * 
                 * resultInteger = SerializeUtil.serialize(new Integer(count)); shardedJedis.set(SerializeUtil.serialize("edf"), resultInteger);
                 * 
                 * shardedJedisContainer.getReadWriteLock().writeLock().unlock();
                 */
            }

            shardedJedisPool.returnResource(shardedJedis);

        } catch (JedisConnectionException e) {

        }

        count = staffMapper.selectLimitCount(staffEty);
        list = staffMapper.selectByLimit(staffEty);

        return XJsonResultFactory.extgrid(list, count);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "save")
    public @ResponseBody XJsonResult save(@RequestBody final StaffEntry staffEty) throws Exception {

        /*
         * redisTemplate.execute(new RedisCallback<Object>() {
         * 
         * @Override public Object doInRedis(RedisConnection connection) throws DataAccessException {
         * connection.set(redisTemplate.getStringSerializer().serialize("user.uid." + staffEty.getId()),
         * redisTemplate.getStringSerializer().serialize(staffEty.getName())); return null; } });
         */

        if (staffEty.getId() == null) {
            staffMapper.insert(staffEty);
        } else {
            staffMapper.updateById(staffEty);
        }
        return XJsonResultFactory.success();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "delete")
    public @ResponseBody XJsonResult delete(@RequestParam("id") int id) {
        staffMapper.deleteById(id);
        return XJsonResultFactory.success();
    }

    /**
     * 得到详细信息
     */
    @RequestMapping(value = "getDetailInfo")
    public @ResponseBody XJsonResult getDetailInfo(@RequestParam("id") int id) throws Exception {

        /*
         * return redisTemplate.execute(new RedisCallback<XJsonResult>() {
         * 
         * @Override public XJsonResult doInRedis(RedisConnection connection) throws DataAccessException { byte[] key =
         * redisTemplate.getStringSerializer().serialize("user.uid." + id); if (connection.exists(key)) { byte[] value = connection.get(key); String name =
         * redisTemplate.getStringSerializer().deserialize(value); StaffEntry user = new StaffEntry(); user.setName(name); user.setId(id); return
         * XJsonResultFactory.success(user); } return null; } });
         * 
         */

        StaffEntry staffEty = staffMapper.selectById(id);
        return XJsonResultFactory.success(staffEty);

    }

    private void StringOperate() {
        System.out.println("======================String_1==========================");

        System.out.println("=============增=============");

        shardedJedis.set("key001", "value001");
        shardedJedis.set("key002", "value002");
        shardedJedis.set("key003", "value003");
        System.out.println("已新增的3个键值对如下：");
        System.out.println(shardedJedis.get("key001"));
        System.out.println(shardedJedis.get("key002"));
        System.out.println(shardedJedis.get("key003"));

        System.out.println("=============删=============");
        System.out.println("删除key003键值对：" + shardedJedis.del("key003"));
        System.out.println("获取key003键对应的值：" + shardedJedis.get("key003"));

        System.out.println("=============改=============");
        // 1、直接覆盖原来的数据
        System.out.println("直接覆盖key001原来的数据：" + shardedJedis.set("key001", "value001-update"));
        System.out.println("获取key001对应的新值：" + shardedJedis.get("key001"));
        // 2、直接覆盖原来的数据
        System.out.println("在key002原来值后面追加：" + shardedJedis.append("key002", "+appendString"));
        System.out.println("获取key002对应的新值" + shardedJedis.get("key002"));

        // jedis具备的功能shardedJedis中也可直接使用，下面测试一些前面没用过的方法
        System.out.println("======================String_2==========================");

        System.out.println("=============新增键值对时防止覆盖原先值=============");
        System.out.println("原先key301不存在时，新增key301：" + shardedJedis.setnx("key301", "value301"));
        System.out.println("原先key302不存在时，新增key302：" + shardedJedis.setnx("key302", "value302"));
        System.out.println("当key302存在时，尝试新增key302：" + shardedJedis.setnx("key302", "value302_new"));
        System.out.println("获取key301对应的值：" + shardedJedis.get("key301"));
        System.out.println("获取key302对应的值：" + shardedJedis.get("key302"));

        System.out.println("=============超过有效期键值对被删除=============");
        // 设置key的有效期，并存储数据
        System.out.println("新增key303，并指定过期时间为2秒" + shardedJedis.setex("key303", 2, "key303-2second"));
        System.out.println("获取key303对应的值：" + shardedJedis.get("key303"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("3秒之后，获取key303对应的值：" + shardedJedis.get("key303"));

        System.out.println("=============获取原值，更新为新值一步完成=============");
        System.out.println("key302原值：" + shardedJedis.getSet("key302", "value302-after-getset"));
        System.out.println("key302新值：" + shardedJedis.get("key302"));

        System.out.println("=============获取子串=============");
        System.out.println("获取key302对应值中的子串：" + shardedJedis.getrange("key302", 5, 7));
    }

    private void ListOperate() {
        System.out.println("======================List==========================");

        System.out.println("=============增=============");
        shardedJedis.lpush("stringlists", "vector");
        shardedJedis.lpush("stringlists", "ArrayList");
        shardedJedis.lpush("stringlists", "vector");
        shardedJedis.lpush("stringlists", "vector");
        shardedJedis.lpush("stringlists", "LinkedList");
        shardedJedis.lpush("stringlists", "MapList");
        shardedJedis.lpush("stringlists", "SerialList");
        shardedJedis.lpush("stringlists", "HashList");
        shardedJedis.lpush("numberlists", "3");
        shardedJedis.lpush("numberlists", "1");
        shardedJedis.lpush("numberlists", "5");
        shardedJedis.lpush("numberlists", "2");
        System.out.println("所有元素-stringlists：" + shardedJedis.lrange("stringlists", 0, -1));
        System.out.println("所有元素-numberlists：" + shardedJedis.lrange("numberlists", 0, -1));

        System.out.println("=============删=============");
        // 删除列表指定的值 ，第二个参数为删除的个数（有重复时），后add进去的值先被删，类似于出栈
        System.out.println("成功删除指定元素个数-stringlists：" + shardedJedis.lrem("stringlists", 2, "vector"));
        System.out.println("删除指定元素之后-stringlists：" + shardedJedis.lrange("stringlists", 0, -1));
        // 删除区间以外的数据
        System.out.println("删除下标0-3区间之外的元素：" + shardedJedis.ltrim("stringlists", 0, 3));
        System.out.println("删除指定区间之外元素后-stringlists：" + shardedJedis.lrange("stringlists", 0, -1));
        // 列表元素出栈
        System.out.println("出栈元素：" + shardedJedis.lpop("stringlists"));
        System.out.println("元素出栈后-stringlists：" + shardedJedis.lrange("stringlists", 0, -1));

        System.out.println("=============改=============");
        // 修改列表中指定下标的值
        shardedJedis.lset("stringlists", 0, "hello list!");
        System.out.println("下标为0的值修改后-stringlists：" + shardedJedis.lrange("stringlists", 0, -1));
        System.out.println("=============查=============");
        // 数组长度
        System.out.println("长度-stringlists：" + shardedJedis.llen("stringlists"));
        System.out.println("长度-numberlists：" + shardedJedis.llen("numberlists"));
        // 排序
        /*
         * list中存字符串时必须指定参数为alpha，如果不使用SortingParams，而是直接使用sort("list")， 会出现"ERR One or more scores can't be converted into double"
         */
        SortingParams sortingParameters = new SortingParams();
        sortingParameters.alpha();
        sortingParameters.limit(0, 3);
        System.out.println("返回排序后的结果-stringlists：" + shardedJedis.sort("stringlists", sortingParameters));
        System.out.println("返回排序后的结果-numberlists：" + shardedJedis.sort("numberlists"));
        // 子串： start为元素下标，end也为元素下标；-1代表倒数一个元素，-2代表倒数第二个元素
        System.out.println("子串-第二个开始到结束：" + shardedJedis.lrange("stringlists", 1, -1));
        // 获取列表指定下标的值
        System.out.println("获取下标为2的元素：" + shardedJedis.lindex("stringlists", 2) + "\n");

        System.out.println("=============直接存放对象=============");
    }

    private void SetOperate() {

        System.out.println("======================set==========================");

        System.out.println("=============增=============");
        System.out.println("向sets集合中加入元素element001：" + shardedJedis.sadd("sets", "element001"));
        System.out.println("向sets集合中加入元素element002：" + shardedJedis.sadd("sets", "element002"));
        System.out.println("向sets集合中加入元素element003：" + shardedJedis.sadd("sets", "element003"));
        System.out.println("向sets集合中加入元素element004：" + shardedJedis.sadd("sets", "element004"));
        System.out.println("查看sets集合中的所有元素:" + shardedJedis.smembers("sets"));
        System.out.println();

        System.out.println("=============删=============");
        System.out.println("集合sets中删除元素element003：" + shardedJedis.srem("sets", "element003"));
        System.out.println("查看sets集合中的所有元素:" + shardedJedis.smembers("sets"));
        /*
         * System.out.println("sets集合中任意位置的元素出栈："+shardedJedis.spop("sets"));//注：出栈元素位置居然不定？--无实际意义
         * System.out.println("查看sets集合中的所有元素:"+shardedJedis.smembers("sets"));
         */
        System.out.println();

        System.out.println("=============改=============");
        System.out.println();

        System.out.println("=============查=============");
        System.out.println("判断element001是否在集合sets中：" + shardedJedis.sismember("sets", "element001"));
        System.out.println("循环查询获取sets中的每个元素：");
        Set<String> set = shardedJedis.smembers("sets");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            System.out.println(obj);
        }
        System.out.println();

        System.out.println("=============集合运算=============");
        System.out.println("sets1中添加元素element001：" + shardedJedis.sadd("sets1", "element001"));
        System.out.println("sets1中添加元素element002：" + shardedJedis.sadd("sets1", "element002"));
        System.out.println("sets1中添加元素element003：" + shardedJedis.sadd("sets1", "element003"));
        System.out.println("sets1中添加元素element002：" + shardedJedis.sadd("sets2", "element002"));
        System.out.println("sets1中添加元素element003：" + shardedJedis.sadd("sets2", "element003"));
        System.out.println("sets1中添加元素element004：" + shardedJedis.sadd("sets2", "element004"));
        System.out.println("查看sets1集合中的所有元素:" + shardedJedis.smembers("sets1"));
        System.out.println("查看sets2集合中的所有元素:" + shardedJedis.smembers("sets2"));

    }

    private void MapOperate() {
        System.out.println("======================Map==========================");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "fujianchao");
        map.put("password", "123");
        map.put("age", "12");
        // 存入一个map
        String aa = shardedJedis.hmset("user", map);
        // map key的个数
        System.out.println("map的key的个数" + shardedJedis.hlen("user"));
        // map key
        System.out.println("map的key" + shardedJedis.hkeys("user"));
        // map value
        System.out.println("map的value" + shardedJedis.hvals("user"));
        // (String key, String... fields)返回值是一个list
        List<String> list = shardedJedis.hmget("user", "age", "name");
        System.out.println("redis中key的各个 fields值：" + shardedJedis.hmget("user", "age", "name") + list.size());
        // 删除map中的某一个键 的值 password
        // 当然 (key, fields) 也可以是多个fields
        shardedJedis.hdel("user", "age");
        System.out.println("删除后map的key" + shardedJedis.hkeys("user"));
    }

    private void SortedSetOperate() {
        System.out.println("======================zset==========================");

        System.out.println("=============增=============");
        System.out.println("zset中添加元素element001：" + shardedJedis.zadd("zset", 7.0, "element001"));
        System.out.println("zset中添加元素element002：" + shardedJedis.zadd("zset", 8.0, "element002"));
        System.out.println("zset中添加元素element003：" + shardedJedis.zadd("zset", 2.0, "element003"));
        System.out.println("zset中添加元素element004：" + shardedJedis.zadd("zset", 3.0, "element004"));
        System.out.println("zset集合中的所有元素：" + shardedJedis.zrange("zset", 0, -1));// 按照权重值排序
        System.out.println();

        System.out.println("=============删=============");
        System.out.println("zset中删除元素element002：" + shardedJedis.zrem("zset", "element002"));
        System.out.println("zset集合中的所有元素：" + shardedJedis.zrange("zset", 0, -1));
        System.out.println();

        System.out.println("=============改=============");
        System.out.println();

        System.out.println("=============查=============");
        System.out.println("统计zset集合中的元素中个数：" + shardedJedis.zcard("zset"));
        System.out.println("统计zset集合中权重某个范围内（1.0——5.0），元素的个数：" + shardedJedis.zcount("zset", 1.0, 5.0));
        System.out.println("查看zset集合中element004的权重：" + shardedJedis.zscore("zset", "element004"));
        System.out.println("查看下标1到2范围内的元素值：" + shardedJedis.zrange("zset", 1, 2));

    }

    private void HashOperate() {
        System.out.println("======================hash==========================");

        System.out.println("=============增=============");
        System.out.println("hashs中添加key001和value001键值对：" + shardedJedis.hset("hashs", "key001", "value001"));
        System.out.println("hashs中添加key002和value002键值对：" + shardedJedis.hset("hashs", "key002", "value002"));
        System.out.println("hashs中添加key003和value003键值对：" + shardedJedis.hset("hashs", "key003", "value003"));
        System.out.println("新增key004和4的整型键值对：" + shardedJedis.hincrBy("hashs", "key004", 4l));
        System.out.println("hashs中的所有值：" + shardedJedis.hvals("hashs"));
        System.out.println();

        System.out.println("=============删=============");
        System.out.println("hashs中删除key002键值对：" + shardedJedis.hdel("hashs", "key002"));
        System.out.println("hashs中的所有值：" + shardedJedis.hvals("hashs"));
        System.out.println();

        System.out.println("=============改=============");
        System.out.println("key004整型键值的值增加100：" + shardedJedis.hincrBy("hashs", "key004", 100l));
        System.out.println("hashs中的所有值：" + shardedJedis.hvals("hashs"));
        System.out.println();

        System.out.println("=============查=============");
        System.out.println("判断key003是否存在：" + shardedJedis.hexists("hashs", "key003"));
        System.out.println("获取key004对应的值：" + shardedJedis.hget("hashs", "key004"));
        System.out.println("批量获取key001和key003对应的值：" + shardedJedis.hmget("hashs", "key001", "key003"));
        System.out.println("获取hashs中所有的key：" + shardedJedis.hkeys("hashs"));
        System.out.println("获取hashs中所有的value：" + shardedJedis.hvals("hashs"));
        System.out.println();

    }

}