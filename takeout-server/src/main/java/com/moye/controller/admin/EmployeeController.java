package com.moye.controller.admin;

import com.moye.constant.JwtClaimsConstant;
import com.moye.dto.EmployeeDTO;
import com.moye.dto.EmployeeLoginDTO;
import com.moye.dto.EmployeePageQueryDTO;
import com.moye.entity.Employee;
import com.moye.properties.JwtProperties;
import com.moye.result.PageResult;
import com.moye.result.Result;
import com.moye.service.EmployeeService;
import com.moye.utils.JwtUtil;
import com.moye.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工管理", description = "员工管理API")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "员工登录", description = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "员工退出", description = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增员工", description = "新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
//        System.out.println("当前线程的id：" + Thread.currentThread().getId());
        employeeService.save(employeeDTO);
        return Result.success();

    }


    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("page")
    @Operation(summary = "分页查询员工", description = "分页查询员工")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {

        log.info("分页查询员工：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 修改员工账号状态
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("status/{status}")
    @Operation(summary = "启用禁用员工账号", description = "启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号：{},{}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 根据id查员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查员工信息", description = "根据id查员工信息")
    public Result<Employee> getById(@PathVariable long id) {
        log.info("根据id查员工信息：{}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }


    @PutMapping
    @Operation(summary = "修改员工信息", description = "修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

}
