package com.moye.service;

import com.moye.dto.EmployeeDTO;
import com.moye.dto.EmployeeLoginDTO;
import com.moye.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);
}
