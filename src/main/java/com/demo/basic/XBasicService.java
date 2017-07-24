package com.demo.basic;

import base.redis.ShardedJedisContainer;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.sys.LoginUserEntry;
import redis.clients.jedis.ShardedJedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shsun
 */
public abstract class XBasicService<PARAMETER, RESULT> {


    protected ShardedJedis shardedJedis;

    protected ShardedJedisContainer shardedJedisContainer;


    public XBasicService() {

    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> retrieveViewData(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            String contextPath = req.getContextPath();
            data.put("contextPath", contextPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @throws Exception
     */
    public abstract void create(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception;

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @return
     * @throws Exception
     */
    public abstract RESULT retrieveByPK(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception;

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @return
     * @throws Exception
     */
    public Page retrievePage(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception {
        Page<RESULT> page = new Page<RESULT>();
        List<RESULT> records = retrieve(req, res, u, p);
        page.setRecords(records);
        return page;
    }

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @return
     * @throws Exception
     */
    public abstract List<RESULT> retrieve(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception;

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @return
     * @throws Exception
     */
    public RESULT updateAndGet(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception {
        this.update(req, res, u, p);
        RESULT result = this.retrieveByPK(req, res, u, p);
        return result;
    }

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @return
     * @throws Exception
     */
    public abstract void update(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception;

    /**
     *
     * @param req
     * @param res
     * @param u
     * @param p
     * @throws Exception
     */
    public RESULT deleteAndGet(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception {
        RESULT result = this.retrieveByPK(req, res, u, p);
        this.delete(req, res, u, p);
        return result;
    }

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @throws Exception
     */
    public abstract void delete(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception;

    /**
     * @param req
     * @param res
     * @param u
     * @param p
     * @return
     * @throws Exception
     */
    public abstract int sumOne(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception;

    /**
     * 
     * @param req
     * @param res
     * @param u
     * @param p
     * @return
     * @throws Exception
     */
    public abstract ISumEntry sumMultiple(HttpServletRequest req, HttpServletResponse res, LoginUserEntry u, PARAMETER p) throws Exception;
}