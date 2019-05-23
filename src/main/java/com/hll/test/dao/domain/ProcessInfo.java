package com.hll.test.dao.domain;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table processinfo
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class ProcessInfo {
    /**
     * Database Column Remarks:
     *   流程id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.processID
     *
     * @mbg.generated
     */
    private Integer processID;

    /**
     * Database Column Remarks:
     *   当前任务类型 0需求 1开发 2测试 3bug
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.currentTaskType
     *
     * @mbg.generated
     */
    private Integer currentTaskType;

    /**
     * Database Column Remarks:
     *   开发任务id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.devTaskID
     *
     * @mbg.generated
     */
    private Integer devTaskID;

    /**
     * Database Column Remarks:
     *   开发人员id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.devUserID
     *
     * @mbg.generated
     */
    private Integer devUserID;

    /**
     * Database Column Remarks:
     *   测试任务id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.testTaskID
     *
     * @mbg.generated
     */
    private Integer testTaskID;

    /**
     * Database Column Remarks:
     *   测试人员id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.testUserID
     *
     * @mbg.generated
     */
    private Integer testUserID;

    /**
     * Database Column Remarks:
     *   是否完成 0未完成 1完成
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.isComplete
     *
     * @mbg.generated
     */
    private Integer isComplete;

    /**
     * Database Column Remarks:
     *   创始人id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.createUserID
     *
     * @mbg.generated
     */
    private Integer createUserID;

    /**
     * Database Column Remarks:
     *   文件地址(文件夹 流程id;文件 文件名+上传人id)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column processinfo.fileUrl
     *
     * @mbg.generated
     */
    private String fileUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.processID
     *
     * @return the value of processinfo.processID
     *
     * @mbg.generated
     */
    public Integer getProcessID() {
        return processID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.processID
     *
     * @param processID the value for processinfo.processID
     *
     * @mbg.generated
     */
    public void setProcessID(Integer processID) {
        this.processID = processID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.currentTaskType
     *
     * @return the value of processinfo.currentTaskType
     *
     * @mbg.generated
     */
    public Integer getCurrentTaskType() {
        return currentTaskType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.currentTaskType
     *
     * @param currentTaskType the value for processinfo.currentTaskType
     *
     * @mbg.generated
     */
    public void setCurrentTaskType(Integer currentTaskType) {
        this.currentTaskType = currentTaskType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.devTaskID
     *
     * @return the value of processinfo.devTaskID
     *
     * @mbg.generated
     */
    public Integer getDevTaskID() {
        return devTaskID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.devTaskID
     *
     * @param devTaskID the value for processinfo.devTaskID
     *
     * @mbg.generated
     */
    public void setDevTaskID(Integer devTaskID) {
        this.devTaskID = devTaskID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.devUserID
     *
     * @return the value of processinfo.devUserID
     *
     * @mbg.generated
     */
    public Integer getDevUserID() {
        return devUserID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.devUserID
     *
     * @param devUserID the value for processinfo.devUserID
     *
     * @mbg.generated
     */
    public void setDevUserID(Integer devUserID) {
        this.devUserID = devUserID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.testTaskID
     *
     * @return the value of processinfo.testTaskID
     *
     * @mbg.generated
     */
    public Integer getTestTaskID() {
        return testTaskID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.testTaskID
     *
     * @param testTaskID the value for processinfo.testTaskID
     *
     * @mbg.generated
     */
    public void setTestTaskID(Integer testTaskID) {
        this.testTaskID = testTaskID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.testUserID
     *
     * @return the value of processinfo.testUserID
     *
     * @mbg.generated
     */
    public Integer getTestUserID() {
        return testUserID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.testUserID
     *
     * @param testUserID the value for processinfo.testUserID
     *
     * @mbg.generated
     */
    public void setTestUserID(Integer testUserID) {
        this.testUserID = testUserID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.isComplete
     *
     * @return the value of processinfo.isComplete
     *
     * @mbg.generated
     */
    public Integer getIsComplete() {
        return isComplete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.isComplete
     *
     * @param isComplete the value for processinfo.isComplete
     *
     * @mbg.generated
     */
    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.createUserID
     *
     * @return the value of processinfo.createUserID
     *
     * @mbg.generated
     */
    public Integer getCreateUserID() {
        return createUserID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.createUserID
     *
     * @param createUserID the value for processinfo.createUserID
     *
     * @mbg.generated
     */
    public void setCreateUserID(Integer createUserID) {
        this.createUserID = createUserID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column processinfo.fileUrl
     *
     * @return the value of processinfo.fileUrl
     *
     * @mbg.generated
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column processinfo.fileUrl
     *
     * @param fileUrl the value for processinfo.fileUrl
     *
     * @mbg.generated
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }
}