package cn.edu.sju.jee.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private Integer posId;
    
    @NotBlank(message = "职位名称不能为空")
    @Length(min = 1, max = 50, message = "职位名称长度在1-50之间")
    private String posName;
    
    @Length(max = 200, message = "职位描述长度不能超过200")
    private String posDesc;
    
    @NotNull(message = "所属部门不能为空")
    private Integer deptId;
    
    private Department department;
    private Date createTime;
    private Date updateTime;
} 