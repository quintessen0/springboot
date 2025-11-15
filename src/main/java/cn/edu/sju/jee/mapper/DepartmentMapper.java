package cn.edu.sju.jee.mapper;

import cn.edu.sju.jee.model.Department;

import java.util.List;

public interface DepartmentMapper {
    List<Department> queryAll();
    int add(Department department);
    int deleteById(Integer deptId);
    Department queryById(Integer deptId);
    int update(Department department);
} 