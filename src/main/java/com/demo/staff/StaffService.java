package com.demo.staff;

import com.demo.basic.ISumEntry;
import com.demo.user.UserEntry;
import com.demo.basic.XBasicService;
import org.apache.ibatis.session.SqlSessionFactory;
import redis.clients.jedis.ShardedJedisPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by shsun on 7/9/17.
 */
@Transactional
@Service
public class StaffService extends XBasicService<StaffEntry, StaffEntry> {

    @Autowired
    private StaffMapper mapper;

    @Autowired
    protected ShardedJedisPool shardedJedisPool;

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    @Override
    public void create(HttpServletRequest req, HttpServletResponse res, UserEntry u, StaffEntry p) throws Exception {
        this.mapper.insert(p);
    }

    @Override
    public StaffEntry retrieveByPK(HttpServletRequest req, HttpServletResponse res, UserEntry u, StaffEntry p) throws Exception {
        return this.mapper.selectById(p.getId());
    }

    @Override
    public List<StaffEntry> retrieveList(HttpServletRequest req, HttpServletResponse res, UserEntry u, StaffEntry p) throws Exception {
        return this.mapper.selectByEntity(p);
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse res, UserEntry u, StaffEntry p) throws Exception {
        this.mapper.updateById(p);
    }

    @Override
    public void delete(HttpServletRequest req, HttpServletResponse res, UserEntry u, StaffEntry p) throws Exception {
        this.mapper.deleteById(p.getId());
    }

    @Override
    public int sumOne(HttpServletRequest req, HttpServletResponse res, UserEntry u, StaffEntry p) throws Exception {
        return 0;
    }

    @Override
    public ISumEntry sumMultiple(HttpServletRequest req, HttpServletResponse res, UserEntry u, StaffEntry p) throws Exception {
        return null;
    }
}
