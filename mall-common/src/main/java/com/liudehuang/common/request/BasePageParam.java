package com.liudehuang.common.request;

import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:36
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:36
 * @UpdateRemark:
 * @Version:
 */
@ToString
public class BasePageParam implements Serializable {

    private static final long serialVersionUID = 7261911011840574796L;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页查询记录数
     */
    private Integer pageSize;

    /**
     * 查询开始记录行号
     */
    private Integer startRow;

    /**
     * 模糊查询关键字
     */
    private String keyword;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * GET 查询开始记录行号
     *
     * @return
     */
    public Integer getStartRow() {
        return startRow = (null == this.getPageNum() || 0 == this.getPageNum() ? 0
                : (this.getPageNum() - 1)) * (null == this.getPageSize() ? 10 : this.getPageSize());
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }
}