package cn.edu.sju.jee.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer empId;
    
    @NotBlank(message = "员工姓名不能为空")
    @Length(min = 1, max = 50, message = "员工姓名长度在1-50之间")
    private String empName;
    
    private String gender;
    
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", 
             message = "身份证号格式不正确")
    private String idCard;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Length(max = 200, message = "地址长度不能超过200")
    private String address;
    
    @NotNull(message = "所属部门不能为空")
    private Integer deptId;
    
    @NotNull(message = "职位不能为空")
    private Integer posId;
    
   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    
    private Integer status; // 1-在职 2-离职
    
    private Department department;
    private Position position;
    private Date createTime;
    private Date updateTime;
} 