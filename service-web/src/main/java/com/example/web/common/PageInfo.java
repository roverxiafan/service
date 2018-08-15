package com.example.web.common;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.List;

/**
 * @title: PageInfo
 * @Description: 分页信息
 * @author: roverxiafan
 * @date: 2016/9/5 10:31
 */
@Data
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = -2582796922824547679L;

    /**
     * 总条数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 每页条数
     */
    @Range(min = 1, max = 200, message = "无效的pageSize")
    private int pageSize = 20;
    /**
     * 当前页数
      */
    @Range(min = 1, message = "无效的curPage")
    private int curPage = 1;
    /**
     * 结果数据
     */
    private List<T> list;
}
