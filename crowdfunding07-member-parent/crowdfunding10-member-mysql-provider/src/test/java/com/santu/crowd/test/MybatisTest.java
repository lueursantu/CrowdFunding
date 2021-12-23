package com.santu.crowd.test;

import com.santu.crowd.entity.po.MemberPO;
import com.santu.crowd.mapper.MemberPOMapper;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Santu
 * @date 2021/12/20 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    MemberPOMapper memberPOMapper;

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testMyBatis01(){
        int i = memberPOMapper.insert(new MemberPO(null, "test", "test", "test", "test", 1, 1, "test", "test", 1));
        System.err.println("操作"+i+"完成");
    }
}
