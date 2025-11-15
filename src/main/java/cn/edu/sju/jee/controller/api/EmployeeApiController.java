package cn.edu.sju.jee.controller.api;

import cn.edu.sju.jee.model.ApiResponse;
import cn.edu.sju.jee.model.Employee;
import cn.edu.sju.jee.model.dto.EmployeeQueryParam;
import cn.edu.sju.jee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeApiController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 获取所有员工
     */
    @GetMapping
    public ApiResponse<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.queryAll();
            return ApiResponse.success("获取员工列表成功", employees);
        } catch (Exception e) {
            log.error("获取员工列表失败: {}", e.getMessage());
            return ApiResponse.error("获取员工列表失败");
        }
    }

    /**
     * 条件查询员工
     */
    @GetMapping("/query")
    public ApiResponse<List<Employee>> queryEmployees(EmployeeQueryParam param) {
        try {
            List<Employee> employees = employeeService.queryByCondition(param);
            return ApiResponse.success("查询员工成功", employees);
        } catch (Exception e) {
            log.error("查询员工失败: {}", e.getMessage());
            return ApiResponse.error("查询员工失败");
        }
    }

    /**
     * 新增员工
     */
    @PostMapping
    public ApiResponse<String> addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.add(employee);
            return ApiResponse.success("新增员工成功");
        } catch (RuntimeException e) {
            log.error("新增员工失败: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error("新增员工失败: {}", e.getMessage());
            return ApiResponse.error("新增员工失败: " + e.getMessage());
        }
    }

    /**
     * 更新员工
     */
    @PutMapping
    public ApiResponse<String> updateEmployee(@RequestBody Employee employee) {
        try {
            employeeService.update(employee);
            return ApiResponse.success("更新员工成功");
        } catch (RuntimeException e) {
            log.error("更新员工失败: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error("更新员工失败: {}", e.getMessage());
            return ApiResponse.error("更新员工失败: " + e.getMessage());
        }
    }

    /**
     * 删除员工
     */
    @DeleteMapping("/{empId}")
    public ApiResponse<String> deleteEmployee(@PathVariable Integer empId) {
        try {
            employeeService.deleteById(empId);
            return ApiResponse.success("删除员工成功");
        } catch (Exception e) {
            log.error("删除员工失败: {}", e.getMessage());
            return ApiResponse.error("删除员工失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取员工
     */
    @GetMapping("/{empId}")
    public ApiResponse<Employee> getEmployeeById(@PathVariable Integer empId) {
        try {
            Employee employee = employeeService.queryById(empId);
            if (employee != null) {
                return ApiResponse.success("获取员工信息成功", employee);
            } else {
                return ApiResponse.error("员工不存在");
            }
        } catch (Exception e) {
            log.error("获取员工信息失败: {}", e.getMessage());
            return ApiResponse.error("获取员工信息失败");
        }
    }
}