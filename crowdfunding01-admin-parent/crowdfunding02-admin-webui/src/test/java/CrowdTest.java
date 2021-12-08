import com.santu.crowd.entity.Admin;
import com.santu.crowd.entity.Role;
import com.santu.crowd.mapper.AdminMapper;
import com.santu.crowd.mapper.AuthMapper;
import com.santu.crowd.service.api.AdminService;
import com.santu.crowd.service.api.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Santu
 * @date 2021/11/11 9:43
 */

//Spring整合JUnit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthMapper authMapper;

    @Test
    public void saveAdminTest(){
        Admin ft = new Admin(null, "ft", "1234"
                , "冯涛", "123@qq.com", null);
        adminService.saveAdmin(ft);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testMapper(){
        Admin admin = new Admin(null, "fengtao", "123"
                , "fengtao", "123@qq.com", null);
        int count = adminMapper.insert(admin);
        System.out.println("受影响行数："+count);
    }

    @Test
    public void testLog(){
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("---------这是logger.debug");
        logger.info("---------这是logger.info");
        logger.warn("---------这是logger.warn");
        logger.error("---------这是logger.error");
    }

    @Test
    public void insertItemToAdmin(){
        for(int i=0;i<247;i++)
            adminService.saveAdmin(new Admin(null, "la"+i, "up"+i, "un"+i, "em"+i, "cm"+i));
    }

    @Test
    public void insertItemToRole(){
        for(int i=0;i<65;i++)
            roleService.saveRole(new Role(null, "name"+i));
    }

    @Test
    public void testSql(){
        Integer roleId = 3;
        List<Integer> authIdList = authMapper.selectAssignedAuthIdByRoleId(roleId);
        System.out.println(authIdList);
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    public void getPassword(){
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

}
