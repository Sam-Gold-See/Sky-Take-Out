package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录DTO对象
     * @return Result类响应对象
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employeeDTO 员工DTO对象
     */
    void addEmployee(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询DTO
     * @return PageResult类响应对象
     */
    PageResult getEmployeeListPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     *
     * @param status 目标启用/禁用状态信息
     * @param id     员工id
     */
    void startOrStopEmployee(Integer status, Long id);

    /**
     * 根据id查询员工
     *
     * @param id 员工id
     * @return Employee类实体对象
     */
    Employee getEmployeeById(Long id);

    /**
     * 编辑员工信息
     *
     * @param employeeDTO 员工DTO对象
     */
    void updateEmployee(EmployeeDTO employeeDTO);
}
