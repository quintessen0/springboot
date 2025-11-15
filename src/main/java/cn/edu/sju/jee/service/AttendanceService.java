package cn.edu.sju.jee.service;

import cn.edu.sju.jee.model.Attendance;
import cn.edu.sju.jee.model.PageResult;

import java.util.List;

public interface AttendanceService {
    List<Attendance> queryAll();
    PageResult<Attendance> queryByPage(int page, int size);
    void add(Attendance attendance);
    void deleteById(Integer attId);
    Attendance queryById(Integer attId);
    void update(Attendance attendance);
    List<Attendance> queryByEmpId(Integer empId);
    void deleteByEmpId(Integer empId);
}