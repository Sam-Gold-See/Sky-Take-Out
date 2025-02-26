package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username String类员工用户名
     * @return Employee类实体对象
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     *
     * @param employee 员工实体类对象
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user, status) " +
            "values " +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{status})")
    @AutoFill(OperationType.INSERT)
    void insertUser(Employee employee);

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询DTO
     * @return Page类分页对象
     */
    Page<Employee> getEmployeeListPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据主键id动态修改属性
     *
     * @param employee 员工实体类对象
     */
    @AutoFill(OperationType.UPDATE)
    void updateEmployee(Employee employee);

    /**
     * 根据id查询员工
     *
     * @param id 员工id
     * @return Employee类实体对象
     */
    @Select("select * from employee where id = #{id}")
    Employee getEmployeeById(Long id);

    /**
     * 根据id查询员工密码
     *
     * @param empId 员工id
     * @return 员工原始密码
     */
    @Select("select password from employee where id = #{empId}")
    String getEmployeePasswordById(Long empId);
}
