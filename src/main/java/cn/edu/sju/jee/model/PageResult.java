package cn.edu.sju.jee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装类
 * @param <T> 数据类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    /**
     * 当前页数据
     */
    private List<T> data;
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 每页显示条数
     */
    private int pageSize;
    
    /**
     * 当前页码
     */
    private int currentPage;
    
    /**
     * 总页数
     */
    private int totalPages;
}