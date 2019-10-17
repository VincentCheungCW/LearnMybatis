package mapper;

import bean.User;

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

}
