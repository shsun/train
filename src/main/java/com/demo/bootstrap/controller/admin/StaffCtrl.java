package com.demo.bootstrap.controller.admin;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.bootstrap.dao.entity.StaffEty;
import com.demo.bootstrap.dao.mapper.base.StaffMapper;
import com.youdo.utils.jsonresult.XJsonResult;
import com.youdo.utils.jsonresult.XJsonResultFactory;

/**
 * 员工管理
 */
@Controller
@RequestMapping("/admin/StaffCtrl/")
public class StaffCtrl {

	@Autowired
	private StaffMapper staffMapper;
	
	
    // @Autowired
    // protected RedisTemplate<Serializable, Serializable> redisTemplate;

	
	/**
	 * 查询
	 */
	@RequestMapping(value="search")
	public @ResponseBody XJsonResult search(@RequestBody final StaffEty staffEty) throws Exception {
				  
		
		/*
		return redisTemplate.execute(new RedisCallback<JsonResult>() {
            @Override
            public JsonResult doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + staffEty.getId());
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String name = redisTemplate.getStringSerializer().deserialize(value);
                    StaffEty user = new StaffEty();
                    user.setName(name);
                    user.setId(id);
                    return null;
                }
                return null;
            }
        });
		*/
		
		int count = staffMapper.selectLimitCount(staffEty);
		List<StaffEty> list = staffMapper.selectByLimit(staffEty);
		return XJsonResultFactory.extgrid(list, count);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="save")
	public @ResponseBody XJsonResult save(@RequestBody final StaffEty staffEty) throws Exception {
		
		

		/*
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(redisTemplate.getStringSerializer().serialize("user.uid." + staffEty.getId()),
						redisTemplate.getStringSerializer().serialize(staffEty.getName()));
				return null;
			}
		});				
		*/
		
		
		if(staffEty.getId() == null) {
			staffMapper.insert(staffEty);
		}
		else {
			staffMapper.updateById(staffEty);
		}
		return XJsonResultFactory.success();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete")
	public @ResponseBody XJsonResult delete(@RequestParam("id") int id) {
		staffMapper.deleteById(id);
		return XJsonResultFactory.success();
	}
	
	/**
	 * 得到详细信息
	 */
	@RequestMapping(value="getDetailInfo")
	public @ResponseBody XJsonResult getDetailInfo(@RequestParam("id") int id) throws Exception {
		StaffEty staffEty = staffMapper.selectById(id);
		return XJsonResultFactory.success(staffEty);
	}
	
}