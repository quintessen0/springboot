package cn.edu.sju.jee.service.impl;

import cn.edu.sju.jee.mapper.DepartmentMapper;
import cn.edu.sju.jee.model.Department;
import cn.edu.sju.jee.service.DepartmentService;
import cn.edu.sju.jee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<Department> queryAll() {
        return departmentMapper.queryAll();
    }

    @Override
    public void add(Department department) {
        departmentMapper.add(department);
    }

    @Override
    @Transactional
    public void deleteById(Integer deptId) {
        // 先删除该部门下的所有员工
        employeeService.deleteByDeptId(deptId);
        // 再删除部门
        departmentMapper.deleteById(deptId);
    }

    @Override
    public Department queryById(Integer deptId) {
        return departmentMapper.queryById(deptId);
    }

    @Override
    public void update(Department department) {
        departmentMapper.update(department);
    }
}