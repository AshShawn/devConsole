package com.hll.test.dao.domain;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table reportinfo
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class ReportInfo {
    /**
     * Database Column Remarks:
     *   提问编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reportinfo.reportID
     *
     * @mbg.generated
     */
    private Integer reportID;

    /**
     * Database Column Remarks:
     *   提问对象
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reportinfo.reportTarget
     *
     * @mbg.generated
     */
    private Integer reportTarget;

    /**
     * Database Column Remarks:
     *   提问类型(0仅组长 1 公开)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reportinfo.reportType
     *
     * @mbg.generated
     */
    private Integer reportType;

    /**
     * Database Column Remarks:
     *   提问内容
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reportinfo.reportContent
     *
     * @mbg.generated
     */
    private String reportContent;

    /**
     * Database Column Remarks:
     *   提问发起人
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reportinfo.createUser
     *
     * @mbg.generated
     */
    private Integer createUser;

    /**
     * Database Column Remarks:
     *   回答
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reportinfo.answer
     *
     * @mbg.generated
     */
    private String answer;

    /**
     * Database Column Remarks:
     *   任务编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reportinfo.taskID
     *
     * @mbg.generated
     */
    private Integer taskID;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reportinfo.reportID
     *
     * @return the value of reportinfo.reportID
     *
     * @mbg.generated
     */
    public Integer getReportID() {
        return reportID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reportinfo.reportID
     *
     * @param reportID the value for reportinfo.reportID
     *
     * @mbg.generated
     */
    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reportinfo.reportTarget
     *
     * @return the value of reportinfo.reportTarget
     *
     * @mbg.generated
     */
    public Integer getReportTarget() {
        return reportTarget;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reportinfo.reportTarget
     *
     * @param reportTarget the value for reportinfo.reportTarget
     *
     * @mbg.generated
     */
    public void setReportTarget(Integer reportTarget) {
        this.reportTarget = reportTarget;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reportinfo.reportType
     *
     * @return the value of reportinfo.reportType
     *
     * @mbg.generated
     */
    public Integer getReportType() {
        return reportType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reportinfo.reportType
     *
     * @param reportType the value for reportinfo.reportType
     *
     * @mbg.generated
     */
    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reportinfo.reportContent
     *
     * @return the value of reportinfo.reportContent
     *
     * @mbg.generated
     */
    public String getReportContent() {
        return reportContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reportinfo.reportContent
     *
     * @param reportContent the value for reportinfo.reportContent
     *
     * @mbg.generated
     */
    public void setReportContent(String reportContent) {
        this.reportContent = reportContent == null ? null : reportContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reportinfo.createUser
     *
     * @return the value of reportinfo.createUser
     *
     * @mbg.generated
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reportinfo.createUser
     *
     * @param createUser the value for reportinfo.createUser
     *
     * @mbg.generated
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reportinfo.answer
     *
     * @return the value of reportinfo.answer
     *
     * @mbg.generated
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reportinfo.answer
     *
     * @param answer the value for reportinfo.answer
     *
     * @mbg.generated
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reportinfo.taskID
     *
     * @return the value of reportinfo.taskID
     *
     * @mbg.generated
     */
    public Integer getTaskID() {
        return taskID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reportinfo.taskID
     *
     * @param taskID the value for reportinfo.taskID
     *
     * @mbg.generated
     */
    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }
}