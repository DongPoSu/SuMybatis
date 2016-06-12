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
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    @Before
    public void setUp(){
        try {

            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userSelectTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            User user = sqlSession.selectOne("com.su.mybatis.nospring.dao.UserMapper.getUserByID",1);
            if (user != null) {
                log.info(user.toString());
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void userSelectDaoTest() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper iUser = sqlSession.getMapper(UserMapper.class);
            User user = iUser.getUserById(1);
            if (user != null) {
                log.info(user.toString());
            }
        }finally {
            sqlSession.close();
        }
    }
}
