package mapper;

import com.moye.TakeoutApplication;
import com.moye.constant.PasswordConstant;
import com.moye.constant.StatusConstant;
import com.moye.entity.Employee;
import com.moye.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@SpringBootTest(classes = TakeoutApplication.class)  //springboot下测试环境注解
public class 员工模块 {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void TestInsert() {
        System.out.println(("----- selectAll method test ------"));

        Employee employee = new Employee();

        employee.setUsername("test1");
        employee.setName("测试账号");
        employee.setPhone("12345678901");
        employee.setIdNumber("123456789012345678");
        employee.setSex("1");

        //设置账号状态等信息
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //TODO:后期需要改为当前登录人的id
        employee.setCreateUser(100L);
        employee.setUpdateUser(100L);

        employeeMapper.insert(employee);
//        System.out.println("insert = " + insert);

    }
}
