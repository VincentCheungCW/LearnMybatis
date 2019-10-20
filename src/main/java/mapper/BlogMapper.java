package mapper;

import bean.Blog;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by Jiang on 2019-10-20.
 */
public interface BlogMapper {

    Blog findById(Long id);

    /**
     * 查询某个用户按照id升序排序的所有文章中，第limit篇开始的共offset篇博客
     * @param ownerId
     * @param limit
     * @param offset
     * @return
     */
    List<Blog> findByUserId(Long ownerId, Integer limit, Integer offset);

    /**
     * 查询某个用户按照id升序排序的所有文章中，
     * 第rowBounds.getLimit()篇开始的共rowBounds.getOffset()篇博客
     * 这样sql中就不用写limit
     * mybatis把所有符合条件的数据都取出来了，然后根据 RowBounds 对象的内容取出部分数据返回。
     * 这种逻辑分页很明显是有问题的，比如有10000000条数据满足条件，这么大的数据量全部从mysql取出来
     * 耗时很长，从mysql传到java程序耗时很长，java程序的内存占用也会变很高，所以不推荐这种实现
     * @param ownerId
     * @param rowBounds
     * @return
     */
    List<Blog> findByUserIdWithRowBounds(Long ownerId, RowBounds rowBounds);


}
