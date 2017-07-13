package base.dao;

import java.util.List;

public interface XBaseMapper<T> {

    Integer insert(T t);

    Integer updateById(T t);

    void deleteById(Integer id);

    T selectById(Integer id);

    List<T> selectByEntity(T t);

    /**
     * 由分页信息查询分页记录
     * 
     * @param t
     * @return
     */
    List<T> selectByLimit(T t);

    /**
     * 为分页查询出记录总数
     * 
     * @param t
     * @return
     */
    Integer selectLimitCount(T t);
}
