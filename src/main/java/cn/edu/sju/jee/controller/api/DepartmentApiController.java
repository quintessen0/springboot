package cn.edu.sju.jee.controller.api;

import cn.edu.sju.jee.model.ApiResponse;
import cn.edu.sju.jee.model.Department;
import cn.edu.sju.jee.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@Slf4j
public class DepartmentApiController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取所有部门
     */
    @GetMapping("/list")
    public ApiResponse<List<Department>> getAllDepartments() {
        try {
            List<Department> departments = departmentService.queryAll();
            return ApiResponse.success("获取部门列表成功", departments);
        } catch (Exception e) {
            log.error("获取部门列表失败: {}", e.getMessage());
            return ApiResponse.error("获取部门列表失败");
        }
    }

    /**
     * 新增部门
     */
    @PostMapping("/add")
    public ApiResponse<String> addDepartment(@RequestBody Department department) {
        try {
            departmentService.add(department);
            return ApiResponse.success("新增部门成功");
        } catch (Exception e) {
            log.error("新增部门失败: {}", e.getMessage());
            return ApiResponse.error("新增部门失败: " + e.getMessage());
        }
    }

    /**
     * 更新部门
     */
    @PutMapping("/update")
    public ApiResponse<String> updateDepartment(@RequestBody Department department) {
        try {
            departmentService.update(department);
            return ApiResponse.success("更新部门成功");
        } catch (Exception e) {
            log.error("更新部门失败: {}", e.getMessage());
            return ApiResponse.error("更新部门失败: " + e.getMessage());
        }
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/delete/{deptId}")
    public ApiResponse<String> deleteDepartment(@PathVariable Integer deptId) {
        try {
            departmentService.deleteById(deptId);
            return ApiResponse.success("删除部门成功");
        } catch (Exception e) {
            log.error("删除部门失败: {}", e.getMessage());
            return ApiResponse.error("删除部门失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取部门
     */
    @GetMapping("/{deptId}")
    public ApiResponse<Department> getDepartmentById(@PathVariable Integer deptId) {
        try {
            Department department = departmentService.queryById(deptId);
            if (department != null) {
                return ApiResponse.success("获取部门信息成功", department);
            } else {
                return ApiResponse.error("部门不存在");
            }
        } catch (Exception e) {
            log.error("获取部门信息失败: {}", e.getMessage());
            return ApiResponse.error("获取部门信息失败");
        }
    }
}