package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EditPasswordDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录DTO对象
     * @return employee类实体对象
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对，将密码通过MD5加密成暗文存入数据库
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 员工DTO对象
     */
    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置账号的状态，默认正常状态 1表示正常 0表示异常
        employee.setStatus(StatusConstant.ENABLE);

        //设置密码，默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        employeeMapper.insertUser(employee);
    }

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询DTO
     * @return PageResult类响应对象
     */
    @Override
    public PageResult getEmployeeListPage(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.getEmployeeListPage(employeePageQueryDTO);

        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 启用禁用员工账号
     *
     * @param status 目标启用/禁用状态信息
     * @param id     员工id
     */
    @Override
    public void startOrStopEmployee(Integer status, Long id) {

        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);

        /*
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        */

        employeeMapper.updateEmployee(employee);
    }

    /**
     * 根据id查询员工
     *
     * @param id 员工id
     * @return Employee类实体对象
     */
    @Override
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 编辑员工信息
     *
     * @param employeeDTO 员工DTO对象
     */
    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        employeeMapper.updateEmployee(employee);
    }

    /**
     * 修改密码
     *
     * @param editPasswordDTO 修改密码数据传输类
     */
    @Override
    public void updateEmployeePassword(EditPasswordDTO editPasswordDTO) {
        Long empId = editPasswordDTO.getEmpId();

        Employee employee = employeeMapper.getEmployeeById(empId);

        if (employee == null)
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);

        String oldPassword = DigestUtils.md5DigestAsHex(editPasswordDTO.getOldPassword().getBytes());
        String oldPasswordTrue = employee.getPassword();

        if (!Objects.equals(oldPasswordTrue, oldPassword))
            throw new PasswordErrorException(MessageConstant.OLD_PASSWORD_ERROR);

        String newPassword = DigestUtils.md5DigestAsHex(editPasswordDTO.getNewPassword().getBytes());

        employee.setId(empId);
        employee.setPassword(newPassword);

        employeeMapper.updateEmployee(employee);
    }
}
