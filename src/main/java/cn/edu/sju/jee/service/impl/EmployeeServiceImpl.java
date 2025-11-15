package cn.edu.sju.jee.service.impl;

import cn.edu.sju.jee.mapper.EmployeeMapper;
import cn.edu.sju.jee.model.Employee;
import cn.edu.sju.jee.model.dto.EmployeeQueryParam;
import cn.edu.sju.jee.service.AttendanceService;
import cn.edu.sju.jee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Autowired
    private AttendanceService attendanceService;

    @Override
    public List<Employee> queryAll() {
        return employeeMapper.queryAll();
    }

    @Override
    @Transactional
    public void add(Employee employee) {
        // 检查身份证是否已存在
        Employee existingEmployee = employeeMapper.queryByIdCard(employee.getIdCard());
        if (existingEmployee != null) {
            throw new RuntimeException("身份证号已存在，不能重复添加");
        }
        employeeMapper.add(employee);
    }

    @Override
    @Transactional
    public void deleteById(Integer empId) {
        // 先删除该员工的所有考勤记录
        attendanceService.deleteByEmpId(empId);
        // 再删除员工
        employeeMapper.deleteById(empId);
    }

    @Override
    @Transactional
    public void deleteByDeptId(Integer deptId) {
        // 删除该部门下的所有员工（会级联删除考勤记录）
        List<Employee> employees = employeeMapper.queryByDeptId(deptId);
        for (Employee employee : employees) {
            deleteById(employee.getEmpId());
        }
    }

    @Override
    public Employee queryById(Integer empId) {
        return employeeMapper.queryById(empId);
    }

    @Override
    @Transactional
    public void update(Employee employee) {
        // 检查身份证是否被其他员工使用
        Employee existingEmployee = employeeMapper.queryByIdCard(employee.getIdCard());
        if (existingEmployee != null && !existingEmployee.getEmpId().equals(employee.getEmpId())) {
            throw new RuntimeException("身份证号已被其他员工使用，不能重复");
        }
        employeeMapper.update(employee);
    }

    @Override
    public List<Employee> queryByCondition(EmployeeQueryParam param) {
        return employeeMapper.queryByCondition(param);
    }
}