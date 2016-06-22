package com.su.mybatis.nospring.test;

import com.su.mybatis.nospring.dao.UserMapper;
import com.su.mybatis.nospring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * @Author: suzheng
 * @Version:
 * @Package: com.su.mybatis.nospring.test
 * @Company: SIBU_KANGER
 * @Description:
 * @Date: 2016/06/12
 */
@Slf4j(topic = "UserTest.class")
public class UserTest {
    private static SqlSession sqlSession;

    @Before
    public void setUp() {
        try {

            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userSelectTest() {
        try {
            User user = sqlSession.selectOne("com.su.mybatis.nospring.dao.UserMapper.getUserByID", 1);
            if (user != null) {
                log.info(user.toString());
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void userSelectDaoTest() {
        try {
            UserMapper iUser = sqlSession.getMapper(UserMapper.class);
            User user = iUser.getUserById(1);
            if (user != null) {
                log.info(user.toString());
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testGetUserList() {
        try {
            List<User> userList = sqlSession.getMapper(UserMapper.class).getUserList();
            userList.forEach(o -> log.info(o.toString()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertUser() {
        try {
            User user = new User();
            user.setName("张三");
            user.setDept("夏至");
            user.setWebsite("http://www.xiazhi.com");
            user.setPhone("1420000000");
            sqlSession.getMapper(UserMapper.class).insertUser(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateUser() {
        try {
            User user = new User(1, "李四", "飞机",  "123456511", "http://www.plain.com");
            sqlSession.getMapper(UserMapper.class).updateUser(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
    @Test
    public void testDeleteUser(){
        try {
            sqlSession.getMapper(UserMapper.class).deleteUser(2);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }
    @Test
    public void testGetUser(){
        try {
            User user = sqlSession.getMapper(UserMapper.class).getUser(2);
            log.info(user.toString());
        }finally {
            sqlSession.close();
        }
    }
}
