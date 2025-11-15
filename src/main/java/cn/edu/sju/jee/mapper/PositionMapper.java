package cn.edu.sju.jee.mapper;

import cn.edu.sju.jee.model.Position;

import java.util.List;

public interface PositionMapper {
    List<Position> queryAll();
    int add(Position position);
    int deleteById(Integer posId);
    Position queryById(Integer posId);
    int update(Position position);
    List<Position> queryByDeptId(Integer deptId);
} 