package bean;

import lombok.Data;

@Data
public class User {

    /**
     * 该类中的变量名称和数据库中的user表的列名是一致的。
     * 也可以不一致！通过在UserMapper.xml文件中添加resultMap映射关系，
     * 把下面的name改成userName测试
     */
    private Long id;
    private String userName;
    private String email;
    private String password;

}
