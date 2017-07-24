package base.dao;

        import java.util.List;

public interface XBaseMapper<T> {

    Integer insert(T t);

    Integer updateById(T t);

    void deleteById(Integer id);

    T selectById(Integer id);

    List<T> selectByEntity(T t);

    List<T> selectByLimit(T t);

    Integer selectLimitCount(T t);
}
