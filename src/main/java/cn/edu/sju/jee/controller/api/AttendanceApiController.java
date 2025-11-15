package cn.edu.sju.jee.controller.api;

import cn.edu.sju.jee.model.ApiResponse;
import cn.edu.sju.jee.model.Attendance;
import cn.edu.sju.jee.model.PageResult;
import cn.edu.sju.jee.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@Slf4j
public class AttendanceApiController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 获取所有考勤记录
     */
    @GetMapping
    public ApiResponse<List<Attendance>> getAllAttendances() {
        try {
            List<Attendance> attendances = attendanceService.queryAll();
            return ApiResponse.success("获取考勤记录成功", attendances);
        } catch (Exception e) {
            log.error("获取考勤记录失败: {}", e.getMessage());
            return ApiResponse.error("获取考勤记录失败");
        }
    }

    /**
     * 分页获取考勤记录
     */
    @GetMapping("/page")
    public ApiResponse<PageResult<Attendance>> getAttendancesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageResult<Attendance> result = attendanceService.queryByPage(page, size);
            return ApiResponse.success("获取考勤记录成功", result);
        } catch (Exception e) {
            log.error("获取考勤记录失败: {}", e.getMessage());
            return ApiResponse.error("获取考勤记录失败");
        }
    }

    /**
     * 根据员工ID获取考勤记录
     */
    @GetMapping("/employee/{empId}")
    public ApiResponse<List<Attendance>> getAttendancesByEmp(@PathVariable Integer empId) {
        try {
            List<Attendance> attendances = attendanceService.queryByEmpId(empId);
            return ApiResponse.success("获取员工考勤记录成功", attendances);
        } catch (Exception e) {
            log.error("获取员工考勤记录失败: {}", e.getMessage());
            return ApiResponse.error("获取员工考勤记录失败");
        }
    }

    /**
     * 新增考勤记录
     */
    @PostMapping
    public ApiResponse<String> addAttendance(@RequestBody Attendance attendance) {
        try {
            attendanceService.add(attendance);
            return ApiResponse.success("新增考勤记录成功");
        } catch (Exception e) {
            log.error("新增考勤记录失败: {}", e.getMessage());
            return ApiResponse.error("新增考勤记录失败: " + e.getMessage());
        }
    }

    /**
     * 更新考勤记录
     */
    @PutMapping("/{attId}")
    public ApiResponse<String> updateAttendance(@PathVariable Integer attId, @RequestBody Attendance attendance) {
        try {
            // 确保ID一致
            attendance.setAttId(attId);
            attendanceService.update(attendance);
            return ApiResponse.success("更新考勤记录成功");
        } catch (Exception e) {
            log.error("更新考勤记录失败: {}", e.getMessage());
            return ApiResponse.error("更新考勤记录失败: " + e.getMessage());
        }
    }

    /**
     * 删除考勤记录
     */
    @DeleteMapping("/{attId}")
    public ApiResponse<String> deleteAttendance(@PathVariable Integer attId) {
        try {
            attendanceService.deleteById(attId);
            return ApiResponse.success("删除考勤记录成功");
        } catch (Exception e) {
            log.error("删除考勤记录失败: {}", e.getMessage());
            return ApiResponse.error("删除考勤记录失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取考勤记录
     */
    @GetMapping("/{attId}")
    public ApiResponse<Attendance> getAttendanceById(@PathVariable Integer attId) {
        try {
            Attendance attendance = attendanceService.queryById(attId);
            if (attendance != null) {
                return ApiResponse.success("获取考勤记录成功", attendance);
            } else {
                return ApiResponse.error("考勤记录不存在");
            }
        } catch (Exception e) {
            log.error("获取考勤记录失败: {}", e.getMessage());
            return ApiResponse.error("获取考勤记录失败");
        }
    }
}