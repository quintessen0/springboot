package cn.edu.sju.jee.service.impl;

import cn.edu.sju.jee.mapper.AttendanceMapper;
import cn.edu.sju.jee.model.Attendance;
import cn.edu.sju.jee.model.PageResult;
import cn.edu.sju.jee.service.AttendanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public List<Attendance> queryAll() {
        return attendanceMapper.queryAll();
    }

    @Override
    public PageResult<Attendance> queryByPage(int page, int size) {
        // 开启分页
        PageHelper.startPage(page, size);
        // 查询数据
        List<Attendance> attendances = attendanceMapper.queryAll();
        // 获取分页信息
        PageInfo<Attendance> pageInfo = new PageInfo<>(attendances);
        
        // 封装分页结果
        PageResult<Attendance> pageResult = new PageResult<>();
        pageResult.setData(attendances);
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPageSize(size);
        pageResult.setCurrentPage(page);
        pageResult.setTotalPages(pageInfo.getPages());
        
        return pageResult;
    }

    @Override
    public void add(Attendance attendance) {
        attendanceMapper.add(attendance);
    }

    @Override
    public void deleteById(Integer attId) {
        attendanceMapper.deleteById(attId);
    }

    @Override
    public Attendance queryById(Integer attId) {
        return attendanceMapper.queryById(attId);
    }

    @Override
    public void update(Attendance attendance) {
        attendanceMapper.update(attendance);
    }

    @Override
    public List<Attendance> queryByEmpId(Integer empId) {
        return attendanceMapper.queryByEmpId(empId);
    }

    @Override
    public void deleteByEmpId(Integer empId) {
        attendanceMapper.deleteByEmpId(empId);
    }
}