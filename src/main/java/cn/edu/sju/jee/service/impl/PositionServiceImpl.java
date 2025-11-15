package cn.edu.sju.jee.service.impl;

import cn.edu.sju.jee.mapper.PositionMapper;
import cn.edu.sju.jee.mapper.EmployeeMapper;
import cn.edu.sju.jee.model.Position;
import cn.edu.sju.jee.model.Employee;
import cn.edu.sju.jee.model.dto.EmployeeQueryParam;
import cn.edu.sju.jee.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper positionMapper;
    
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Position> queryAll() {
        return positionMapper.queryAll();
    }

    @Override
    public void add(Position position) {
        positionMapper.add(position);
    }

    @Override
    @Transactional
    public void deleteById(Integer posId) {
        // 先检查是否有员工关联到该职位
        EmployeeQueryParam param = new EmployeeQueryParam();
        param.setPosId(posId);
        List<Employee> employees = employeeMapper.queryByCondition(param);
        if (!employees.isEmpty()) {
            // 如果有员工关联到该职位，抛出异常
            throw new RuntimeException("该职位下还有员工，无法删除");
        }
        // 删除职位
        positionMapper.deleteById(posId);
    }

    @Override
    public Position queryById(Integer posId) {
        return positionMapper.queryById(posId);
    }

    @Override
    public void update(Position position) {
        positionMapper.update(position);
    }

    @Override
    public List<Position> queryByDeptId(Integer deptId) {
        return positionMapper.queryByDeptId(deptId);
    }
}