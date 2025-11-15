package cn.edu.sju.jee.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    private Integer attId;
    
    @NotNull(message = "员工不能为空")
    private Integer empId;
    
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date checkIn;
    
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date checkOut;
    
    private Integer status; // 1-正常 2-迟到 3-早退 4-缺勤
    
    @Length(max = 200, message = "备注长度不能超过200")
    private String remark;
    
    private Date createTime;
    private Employee employee;
}