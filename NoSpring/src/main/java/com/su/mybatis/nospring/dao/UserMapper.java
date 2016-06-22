package com.su.mybatis.nospring.dao;

import com.su.mybatis.nospring.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: suzheng
 * @Version:
 * @Package: com.su.mybatis.nospring.dao
 * @Company: SIBU_KANGER
 * @Description:
 * @Date: 2016/06/12
 */
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    public User getUserById(int id);

    public List<User> getUserList();

    public void insertUser(User user);

    public void updateUser(User user);

    public void deleteUser(int userId);

    public User getUser(int id);
}
