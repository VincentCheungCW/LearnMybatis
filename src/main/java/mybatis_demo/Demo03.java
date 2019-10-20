package mybatis_demo;

import bean.Blog;
import bean.User;
import lombok.extern.slf4j.Slf4j;
import mapper.BlogMapper;
import mapper.UserMapper;
import mapper.UserMapperWithAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * 分页查询
 * Created by Jiang on 2019-10-20.
 */
@Slf4j
public class Demo03 {

    /**
     * 在 sql 中显式使用 limit，物理查询
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        SqlSession sqlSession = getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogList = blogMapper.findByUserId(1L, 0, 2);
        blogList.forEach(item -> {
            log.info("{}", item);
        });
    }

    /**
     * 使用 RowBounds 进行逻辑分页查询
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        SqlSession sqlSession = getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogList = blogMapper.findByUserIdWithRowBounds(1L, new RowBounds(0, 2));
        blogList.forEach(item -> {
            log.info("blog: {}", item);
        });
    }

    /**
     * 使用 RowBounds + PageHelper 进行物理分页查询
     * @throws IOException
     */
    @Test
    public void test_03() throws IOException {
        SqlSession sqlSession = getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogList = blogMapper.findByUserIdWithRowBounds(1L, new RowBounds(0, 2));
        blogList.forEach(item -> {
            log.info("blog: {}", item);
        });
    }

    /**
     * 把SQL写在注解中
     * @throws IOException
     */
    @Test
    public void test_findById() throws IOException {
        try ( SqlSession sqlSession = getSqlSession() ) {
            UserMapperWithAnnotation userMapper = sqlSession.getMapper(UserMapperWithAnnotation.class);
            User user = userMapper.findById(1L);
            log.info("{}", user);
        }
    }

    private SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sessionFactory;
        sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        return sessionFactory.openSession();
    }


}
