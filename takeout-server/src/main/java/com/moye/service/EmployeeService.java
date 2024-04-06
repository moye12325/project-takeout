package com.moye.service;

//import com.baomidou.mybatisplus.extension.service.IService;
import com.moye.dto.EmployeeDTO;
import com.moye.dto.EmployeeLoginDTO;
import com.moye.dto.EmployeePageQueryDTO;
import com.moye.entity.Employee;
import com.moye.result.PageResult;

public interface EmployeeService{

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
}
