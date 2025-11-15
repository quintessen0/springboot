package cn.edu.sju.jee.mapper;

import cn.edu.sju.jee.model.Attendance;

import java.util.List;

public interface AttendanceMapper {
    List<Attendance> queryAll();
    int add(Attendance attendance);
    int deleteById(Integer attId);
    Attendance queryById(Integer attId);
    int update(Attendance attendance);
    List<Attendance> queryByEmpId(Integer empId);
    int deleteByEmpId(Integer empId);
} 