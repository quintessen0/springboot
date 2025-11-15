package cn.edu.sju.jee.controller.api;

import cn.edu.sju.jee.model.ApiResponse;
import cn.edu.sju.jee.model.Position;
import cn.edu.sju.jee.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@Slf4j
public class PositionApiController {

    @Autowired
    private PositionService positionService;

    /**
     * 获取所有职位
     */
    @GetMapping
    public ApiResponse<List<Position>> getAllPositions() {
        try {
            List<Position> positions = positionService.queryAll();
            return ApiResponse.success("获取职位列表成功", positions);
        } catch (Exception e) {
            log.error("获取职位列表失败: {}", e.getMessage());
            return ApiResponse.error("获取职位列表失败");
        }
    }

    /**
     * 根据部门ID获取职位
     */
    @GetMapping("/dept/{deptId}")
    public ApiResponse<List<Position>> getPositionsByDept(@PathVariable Integer deptId) {
        try {
            List<Position> positions = positionService.queryByDeptId(deptId);
            return ApiResponse.success("获取部门职位成功", positions);
        } catch (Exception e) {
            log.error("获取部门职位失败: {}", e.getMessage());
            return ApiResponse.error("获取部门职位失败");
        }
    }

    /**
     * 新增职位
     */
    @PostMapping
    public ApiResponse<String> addPosition(@RequestBody Position position) {
        try {
            positionService.add(position);
            return ApiResponse.success("新增职位成功");
        } catch (Exception e) {
            log.error("新增职位失败: {}", e.getMessage());
            return ApiResponse.error("新增职位失败: " + e.getMessage());
        }
    }

    /**
     * 更新职位
     */
    @PutMapping
    public ApiResponse<String> updatePosition(@RequestBody Position position) {
        try {
            positionService.update(position);
            return ApiResponse.success("更新职位成功");
        } catch (Exception e) {
            log.error("更新职位失败: {}", e.getMessage());
            return ApiResponse.error("更新职位失败: " + e.getMessage());
        }
    }

    /**
     * 删除职位
     */
    @DeleteMapping("/{posId}")
    public ApiResponse<String> deletePosition(@PathVariable Integer posId) {
        try {
            positionService.deleteById(posId);
            return ApiResponse.success("删除职位成功");
        } catch (Exception e) {
            log.error("删除职位失败: {}", e.getMessage());
            return ApiResponse.error("删除职位失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取职位
     */
    @GetMapping("/{posId}")
    public ApiResponse<Position> getPositionById(@PathVariable Integer posId) {
        try {
            Position position = positionService.queryById(posId);
            if (position != null) {
                return ApiResponse.success("获取职位信息成功", position);
            } else {
                return ApiResponse.error("职位不存在");
            }
        } catch (Exception e) {
            log.error("获取职位信息失败: {}", e.getMessage());
            return ApiResponse.error("获取职位信息失败");
        }
    }
}