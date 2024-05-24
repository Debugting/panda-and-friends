package com.example.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * 分页控件 DTO
 */
public class Page implements Serializable {

    private Integer sysUserId;

    private Integer total;// 总条数

    private List rows; // 返回页面的数据

    private Integer page = 1;

    private Integer pageSize = 10;

    @JsonIgnore
    private String startTime;//开始时间

    @JsonIgnore
    private String endTime;//结束时间

    @JsonIgnore
    private String orderByString;//排序


    public String getOrderByString() {
        return orderByString;
    }

    public void setOrderByString(String orderByString) {
        this.orderByString = orderByString;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getstartTime() {
        return startTime;
    }

    public void setstartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getendTime() {
        return endTime;
    }

    public void setendTime(String endTime) {
        this.endTime = endTime;
    }


    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }
}
