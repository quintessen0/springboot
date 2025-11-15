package cn.edu.sju.jee.service;

import cn.edu.sju.jee.model.Employee;
import cn.edu.sju.jee.model.dto.EmployeeQueryParam;

import java.util.List;

public interface EmployeeService {
    List<Employee> queryAll();
    void add(Employee employee);
    void deleteById(Integer empId);
    void deleteByDeptId(Integer deptId);
    Employee queryById(Integer empId);
    void update(Employee employee);
    List<Employee> queryByCondition(EmployeeQueryParam param);
}