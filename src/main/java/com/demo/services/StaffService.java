package com.demo.services;

import base.redis.SerializeUtil;
import base.utils.jsonresult.XJsonResultFactory;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.dao.entity.StaffEty;
import com.demo.dao.mapper.base.StaffMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import redis.clients.jedis.ShardedJedisPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shsun on 7/9/17.
 */
@Transactional
@Service
public class StaffService extends XBasicService<StaffEty, StaffEty> {
    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    protected ShardedJedisPool shardedJedisPool;

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    public Page search(final StaffEty staffEty) throws Exception {

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

        int count;
        List<StaffEty> list;
        if (resultList != null) {
            list = (List<StaffEty>) SerializeUtil.unserialize(resultList);
            count = ((Integer) SerializeUtil.unserialize(resultInteger)).intValue();
        } else {
            count = staffMapper.selectLimitCount(staffEty);
            list = staffMapper.selectByLimit(staffEty);

            /*
             * SqlSession session = sqlSessionFactory.openSession(); try { StaffMapper mapper = session.getMapper(StaffMapper.class); count =
             * mapper.selectLimitCount(staffEty); list = mapper.selectByLimit(staffEty); } finally { session.clearCache(); session.close(); }
             */

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

        return XJsonResultFactory.extgrid(list, count);
    }

    public void create(final StaffEty staffEty) throws Exception {
        if (staffEty.getId() == null) {
            staffMapper.insert(staffEty);
        }
    }

    public void update(final StaffEty staffEty) throws Exception {
        if (staffEty.getId() == null) {
            staffMapper.updateById(staffEty);
        }
    }

    public void delete(final StaffEty staffEty) {
        staffMapper.updateById(staffEty);
    }

    public StaffEty selectByPrimaryKey(final int id) throws Exception {
        StaffEty staffEty = staffMapper.selectById(id);
        return staffEty;
    }

}
