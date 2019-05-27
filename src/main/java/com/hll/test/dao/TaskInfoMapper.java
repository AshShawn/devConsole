package com.hll.test.dao;

import java.util.List;
import java.util.Map;

import com.hll.test.dao.domain.TaskInfo;
import com.hll.test.dao.domain.UserInfo;

public interface TaskInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taskinfo
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer taskID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taskinfo
     *
     * @mbg.generated
     */
    int insert(TaskInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taskinfo
     *
     * @mbg.generated
     */
    int insertSelective(TaskInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taskinfo
     *
     * @mbg.generated
     */
    TaskInfo selectByPrimaryKey(Integer taskID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taskinfo
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TaskInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table taskinfo
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TaskInfo record);

    List<Map<String,Object>> selectAll_page(Map<String, Object> map);

    List<TaskInfo> selectByProcessID(Integer processID);

    List<TaskInfo> selectByUserID(Integer userID);

    List<TaskInfo> selectByTaskType();
}