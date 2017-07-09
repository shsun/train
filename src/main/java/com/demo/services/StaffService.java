package com.demo.services;

import base.redis.SerializeUtil;
import base.utils.jsonresult.XJsonResultFactory;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.dao.entity.StaffEty;
import com.demo.dao.mapper.base.StaffMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import redis.clients.jedis.ShardedJedis;
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
        //
        final int size = staffEty.getExtLimit().getLimit().intValue();
        final int current = staffEty.getExtLimit().getStart().intValue() % size;
        // 
        Page page = new Page(current, size);

        int count;
        List<StaffEty> list;
        count = staffMapper.selectLimitCount(staffEty);
        list = staffMapper.selectByLimit(staffEty);

        page.setRecords(list);
        page.setTotal(count);
        return page;
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
