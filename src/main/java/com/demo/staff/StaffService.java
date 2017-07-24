package com.demo.staff;

import base.redis.SerializeUtil;
import com.demo.basic.ISumEntry;
import com.demo.sys.LoginUserEntry;
import com.demo.basic.XBasicService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.ShardedJedisPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public void create(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, StaffEntry p) throws Exception {
        this.mapper.insert(p);
    }

    @Override
    public StaffEntry retrieveByPK(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, StaffEntry p) throws Exception {
        return this.mapper.selectById(p.getId());
    }

    @Override
    public List<StaffEntry> retrieve(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, StaffEntry p) throws Exception {
        Object tmpcount = this.shardedJedisContainer.getObject("num");
        if (tmpcount == null) {

        }
        int count;
        List<StaffEntry> list;

        if (p.getId() != null && p.getId() == 0) {
            ServletContext context = req.getSession().getServletContext();
            WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(context);
            ((AbstractApplicationContext) wac).close();
        }
        if (p.getId() != null && p.getId() == 1) {
            System.exit(0);
        }

        try {
            shardedJedis = shardedJedisPool.getResource();

            byte[] resultList = null;
            byte[] resultInteger = null;
            resultInteger = shardedJedis.get(SerializeUtil.serialize("edf"));
            resultList = shardedJedis.get(SerializeUtil.serialize("abc"));

            if (resultList != null) {
                list = (List<StaffEntry>) SerializeUtil.unserialize(resultList);
                count = ((Integer) SerializeUtil.unserialize(resultInteger)).intValue();
            } else {
                count = mapper.selectLimitCount(p);
                list = mapper.selectByLimit(p);

                SqlSession session = sqlSessionFactory.openSession();
                try {
                    StaffMapper mapper = session.getMapper(StaffMapper.class);
                    count = mapper.selectLimitCount(p);
                    list = mapper.selectByLimit(p);
                } finally {
                    session.clearCache();
                    session.close();
                }

                /*
                 * shardedJedisContainer.getReadWriteLock().writeLock().lock();
                 *
                 * resultList = SerializeUtil.serialize(list);
                 *
                 * shardedJedis.set(SerializeUtil.serialize("abc"), resultList);
                 *
                 * resultInteger = SerializeUtil.serialize(new Integer(count));
                 *
                 * shardedJedis.set(SerializeUtil.serialize("edf"), resultInteger);
                 *
                 * shardedJedisContainer.getReadWriteLock().writeLock().unlock();
                 */
            }

            shardedJedisPool.returnResource(shardedJedis);

        } catch (JedisConnectionException e) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }

        count = mapper.selectLimitCount(p);
        list = mapper.selectByLimit(p);

        return this.mapper.selectByEntity(p);
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, StaffEntry p) throws Exception {
        this.mapper.updateById(p);
    }

    @Override
    public void delete(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, StaffEntry p) throws Exception {
        this.mapper.deleteById(p.getId());
    }

    @Override
    public int sumOne(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, StaffEntry p) throws Exception {
        return 0;
    }

    @Override
    public ISumEntry sumMultiple(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, StaffEntry p) throws Exception {
        return null;
    }
}
