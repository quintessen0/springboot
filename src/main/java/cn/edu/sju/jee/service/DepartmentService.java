package cn.edu.sju.jee.service;

import cn.edu.sju.jee.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> queryAll();
    void add(Department department);
    void deleteById(Integer deptId);
    Department queryById(Integer deptId);
    void update(Department department);
} 