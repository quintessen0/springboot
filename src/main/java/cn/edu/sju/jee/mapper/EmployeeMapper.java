package cn.edu.sju.jee.mapper;

import cn.edu.sju.jee.model.Employee;
import cn.edu.sju.jee.model.dto.EmployeeQueryParam;

import java.util.List;

public interface EmployeeMapper {
    List<Employee> queryAll();
    int add(Employee employee);
    int deleteById(Integer empId);
    Employee queryById(Integer empId);
    int update(Employee employee);
    Employee queryByIdCard(String idCard);
    int deleteByIdCard(String idCard);
    List<Employee> queryByCondition(EmployeeQueryParam param);
    List<Employee> queryByDeptId(Integer deptId);
}