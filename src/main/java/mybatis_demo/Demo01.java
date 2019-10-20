package mybatis_demo;

import bean.Blog;
import bean.User;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import mapper.BlogMapper;
import mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class Demo01 {

    /**
     * 使用mybatis自己实现的连接池
     *
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory;

        sessionFactory = sqlSessionFactoryBuilder.build(
                Resources.getResourceAsReader("mybatis-config.xml"),
                "development"  // 这个参数可以省略，因为 mybatis-config.xml 的<environments>标签指定了默认环境为development
        );

        SqlSession sqlSession = sessionFactory.openSession();

        // 以上是样板代码
        // 以下是「业务逻辑」

        try {
            //我们只定义了 UserMapper 接口，得到了一个 UserMapper 对象
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.findById(1L);
            log.info("{}", user);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 使用DBCP连接池
     *
     * @throws IOException
     */
    @Test
    public void test_02() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory;
        sessionFactory = sqlSessionFactoryBuilder.build(
                Resources.getResourceAsReader("mybatis-config-dbcp.xml"),
                "development"
        );
        SqlSession sqlSession = sessionFactory.openSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.findById(1L);
            log.info("{}", user);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 用代码替代mybatis-config.xml、mybatis-config-dbcp.xml这种配置文件（推荐）
     */
    @Test
    public void test_03() {
        //mybatis自带的连接池
//        PooledDataSource dataSource = new PooledDataSource();
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
//        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/blog_db?useUnicode=true&characterEncoding=utf8");
//        dataSource.setDriver("com.mysql.jdbc.Driver");
//        dataSource.setPoolMaximumActiveConnections(100);
//        dataSource.setPoolMaximumIdleConnections(8);

        //DBCP连接池
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
//        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/blog_db?useUnicode=true&characterEncoding=utf8");
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setInitialSize(6);
//        dataSource.setMaxIdle(8);
//        dataSource.setMinIdle(6);

        //C3P0连接池
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setUser("root");
//        dataSource.setPassword("");
//        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/blog_db?useUnicode=true&characterEncoding=utf8");
//        try {
//            dataSource.setDriverClass("com.mysql.jdbc.Driver");
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
//        dataSource.setInitialPoolSize(6);
//        dataSource.setMaxPoolSize(100);

        //Hikari连接池
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/blog_db?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession sqlSession = sessionFactory.openSession();
        try {
            //注意，UserMapper.xml 映射文件所在目录必须和对应的 UserMapper 接口在相同位置的目录下，
            // 且要同名。 比如 UserMapper 接口在 java 目录的 mapper 包下，
            // UserMapper.xml 就必须在 resources 目录下的mapper 目录下。
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.findById(1L);
            log.info("{}", user);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询密码为123的所有用户
     *
     * @throws IOException
     */
    @Test
    public void test_04() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findOneUserByPassword("123");
        log.info("{}", user);
        List<User> userList = userMapper.findByPassword("123");
        log.info("{}", userList);
    }

    @Test
    public void test_05() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User letian = userMapper.findByNameAndPasswordV2("letian", "123");
        log.info(letian.toString());

        Map<String, Object> data = new HashMap<>();
        data.put("username", "letian");
        data.put("password", "123");
        User user = userMapper.findByNameAndPasswordV3(data);
        log.info("{}", user);
    }

    /**
     * 对查询结果排序
     *
     * @throws IOException
     */
    @Test
    public void test_06() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.findByPassword("123", "id desc");
        log.info("{}", userList);
    }

    /**
     * 动态SQL
     *
     * @throws IOException
     */
    @Test
    public void test_07() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findByName("letian", null);
        log.info("{}", user);
    }

    /**
     * 动态SQL
     *
     * @throws IOException
     */
    @Test
    public void test_08() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1L);
        List<User> users = userMapper.find(user);
        log.info("{}", users);
    }

    /**
     * 插入数据
     *
     * @throws IOException
     */
    @Test
    public void test_insertUser() throws IOException {
        boolean autoCommit = false;
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession(autoCommit);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("阳阳");
        user.setEmail("xiaowei@111.com");
        user.setPassword("456");

        try {
            int result = userMapper.insertUser(user);
            log.info("result: {}, user: {}", result, user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据 id 更新密码
     * 事务提交、回滚
     *
     * @throws IOException
     */
    @Test
    public void test_updateUser() throws IOException {
        boolean autoCommit = false;
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession(autoCommit);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(3L);
        user.setPassword("123456");
        try {
            int result = userMapper.updateUserPasswordById(user);
            log.info("result: {}, user: {}", result, user);
//            sqlSession.commit();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test_deleteUserById() throws IOException {
        boolean autoCommit = false;
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession(autoCommit);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            int result = userMapper.deleteById(3L);
            log.info("result: {}", result);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test_deleteUserByIdRange() throws IOException {
        boolean autoCommit = false;
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession(autoCommit);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            int result = userMapper.deleteByIdRange(1L, 2L);
            log.info("result: {}", result);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 在User类中添加其他属性
     * @throws IOException
     */
    @Test
    public void test_09() throws IOException {
        boolean autoCommit = false;
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession(autoCommit);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findByIdWithBlog(1L);
        log.info("{}", user);
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void test_10() throws IOException {
        boolean autoCommit = false;
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsReader("mybatis-config-dbcp.xml"));
        SqlSession sqlSession = sessionFactory.openSession(autoCommit);
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.findById(1L);
        log.info("id:{}, title:{}, content:{}", blog.getId(), blog.getTitle(), blog.getContent());
        log.info("user: {}", blog.getUser());
    }

}
