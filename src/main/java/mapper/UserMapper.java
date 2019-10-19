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

    /**
     * 根据密码查询所有用户
     * @param password
     * @return
     */
    List<User> findByPassword(
            @Param("password") String password,
            @Param("orderClause") String orderClause);

    /**
     * 添加用户
     * @return 影响的行数
     */
    int insertUser(User user);

    /**
     * 根据 id 更新密码
     * @return 影响的行数
     */
    int updateUserPasswordById(User user);

    /**
     * 删除指定id的记录
     * @return 影响的行数
     */
    int deleteById(Long id);

    /**
     * 删除id在 [minId, maxId] 范围内的记录
     * @return 影响的行数
     */
    int deleteByIdRange(Long minId, Long maxId);

}
