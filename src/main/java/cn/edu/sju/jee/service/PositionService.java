package cn.edu.sju.jee.service;

import cn.edu.sju.jee.model.Position;

import java.util.List;

public interface PositionService {
    List<Position> queryAll();
    void add(Position position);
    void deleteById(Integer posId);
    Position queryById(Integer posId);
    void update(Position position);
    List<Position> queryByDeptId(Integer deptId);
} 