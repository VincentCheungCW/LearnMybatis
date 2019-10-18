package mapper;

import bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * UserMapper接口中的方法findById与 XML 映射文件resource/mapper/UserMapper.xml对应。
 */
public interface UserMapper {
    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据密码，查询其中一个用户
     * 必须保证最多返回一条数据，否则会报 TooManyResultsException 错误。无数据，则返回null
     * @param password
     * @return
     */
    User findOneUserByPassword(String password);

    /**
     * 根据密码查询所有用户
     * @param password
     * @return
     */
    List<User> findByPassword(String password);

    /**
     * 下面的函数都是根据 name 和 password 查询用户
     */
    User findByNameAndPasswordV1(String name, String password);

    User findByNameAndPasswordV2(@Param("username") String name, @Param("password") String password);

    User findByNameAndPasswordV3(Map<String,Object> data);

    User findByNameAndPasswordV6(User user);

    User findByNameAndPasswordV7(@Param("user") User user);


}
