package com.liudehuang.item.api.model.domain;

import java.util.Date;

public class ItemCollect {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.item_no
     *
     * @mbggenerated
     */
    private String itemNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.collect_flag
     *
     * @mbggenerated
     */
    private Integer collectFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.created_time
     *
     * @mbggenerated
     */
    private Date createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.created_by
     *
     * @mbggenerated
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.updated_time
     *
     * @mbggenerated
     */
    private Date updatedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.updated_by
     *
     * @mbggenerated
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item_collect.deleted
     *
     * @mbggenerated
     */
    private Integer deleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.id
     *
     * @return the value of t_item_collect.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.id
     *
     * @param id the value for t_item_collect.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.item_no
     *
     * @return the value of t_item_collect.item_no
     *
     * @mbggenerated
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.item_no
     *
     * @param itemNo the value for t_item_collect.item_no
     *
     * @mbggenerated
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.user_id
     *
     * @return the value of t_item_collect.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.user_id
     *
     * @param userId the value for t_item_collect.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.collect_flag
     *
     * @return the value of t_item_collect.collect_flag
     *
     * @mbggenerated
     */
    public Integer getCollectFlag() {
        return collectFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.collect_flag
     *
     * @param collectFlag the value for t_item_collect.collect_flag
     *
     * @mbggenerated
     */
    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.created_time
     *
     * @return the value of t_item_collect.created_time
     *
     * @mbggenerated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.created_time
     *
     * @param createdTime the value for t_item_collect.created_time
     *
     * @mbggenerated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.created_by
     *
     * @return the value of t_item_collect.created_by
     *
     * @mbggenerated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.created_by
     *
     * @param createdBy the value for t_item_collect.created_by
     *
     * @mbggenerated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.updated_time
     *
     * @return the value of t_item_collect.updated_time
     *
     * @mbggenerated
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.updated_time
     *
     * @param updatedTime the value for t_item_collect.updated_time
     *
     * @mbggenerated
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.updated_by
     *
     * @return the value of t_item_collect.updated_by
     *
     * @mbggenerated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.updated_by
     *
     * @param updatedBy the value for t_item_collect.updated_by
     *
     * @mbggenerated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.remark
     *
     * @return the value of t_item_collect.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.remark
     *
     * @param remark the value for t_item_collect.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item_collect.deleted
     *
     * @return the value of t_item_collect.deleted
     *
     * @mbggenerated
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item_collect.deleted
     *
     * @param deleted the value for t_item_collect.deleted
     *
     * @mbggenerated
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}