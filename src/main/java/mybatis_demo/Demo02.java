package mybatis_demo;

import lombok.extern.slf4j.Slf4j;
import mapper.StudentMapper;
import mapper.TeacherMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Jiang on 2019-10-20.
 */
@Slf4j
public class Demo02 {

    /**
     * 多对多的实现：一个老师有多个学生，一个学生有多个老师，学生和老师之间就是多对多。
     * 实现上稍微麻烦，以老师和学生为例子，除了在数据库中建立老师表、学生表，还要建立老师和学生之间的关系表。
     * @throws IOException
     */
    @Test
    public void test_01() throws IOException {
        try (SqlSession sqlSession = getSqlSession()) {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            log.info("{}", studentMapper.findById(1L));
            log.info("{}", studentMapper.findById(2L));
        }
    }

    @Test
    public void test_02() throws IOException {
        try (SqlSession sqlSession = getSqlSession()) {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            log.info("{}", teacherMapper.findById(1L));
        }
    }

    private SqlSession getSqlSession() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory;
        sessionFactory = sqlSessionFactoryBuilder.build(
                Resources.getResourceAsReader("mybatis-config3.xml"),
                "development"
        );
        return sessionFactory.openSession();
    }

}
