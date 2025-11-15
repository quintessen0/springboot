package cn.edu.sju.jee.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer deptId;
    
    @NotBlank(message = "部门名称不能为空")
    @Length(min = 1, max = 50, message = "部门名称长度在1-50之间")
    private String deptName;
    
    @Length(max = 200, message = "部门描述长度不能超过200")
    private String deptDesc;
    
    private Date createTime;
    private Date updateTime;
} 