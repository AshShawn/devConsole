package com.hll.test.controller;/**
 * Create by sq598 on 2019/3/12
 */

import java.lang.reflect.Array;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import javafx.concurrent.Task;
import org.apache.taglibs.standard.tag.el.core.IfTag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hll.test.common.*;
import com.hll.test.common.page.Page;
import com.hll.test.dao.ProcessInfoMapper;
import com.hll.test.dao.TaskInfoMapper;
import com.hll.test.dao.UserInfoMapper;
import com.hll.test.dao.domain.ProcessInfo;
import com.hll.test.dao.domain.TaskInfo;
import com.hll.test.dao.domain.UserInfo;
import com.sun.prism.shader.Solid_TextureSecondPassLCD_AlphaTest_Loader;

@Controller
public class TaskController {

    @Resource
    private TaskInfoMapper taskInfoMapper;
    @Resource
    private ProcessInfoMapper processInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    @RequestMapping("/getTaskInfoPage")
    @ResponseBody
    public Response getTaskInfoPage(int page, HttpServletRequest req, String q_taskID) {
        UserInfo userInfo = SessionUtil.getUserinfo(req);
        Integer roleID = userInfo.getRoleID();
        Integer userID = userInfo.getUserID();
        Page p = new Page(page);
        Map<String, Object> map = new HashMap<>();
        if (!StringUtil.isNullOrBlank(q_taskID)) {
            map.put("taskID", Integer.parseInt(q_taskID));
        }
        if (roleID == 3) {
            map.put("userID", "");
        } else {
            map.put("userID", userID);
        }
        Util.removeNullEntry(map);
        map.put(Page.KEY, p);
        List data = taskInfoMapper.selectAll_page(map);
        Iterator iterator = data.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            Map<String, Object> map1 = (Map<String, Object>) item;
            String s = (String) map1.get("workerIDs");
            Integer leaderID = (Integer) map1.get("leaderID");
            String[] split = s.split(",");
            List<String> list = Arrays.asList(split);
            if (list.contains(userID + "") || leaderID == userID) {
                continue;
            } else {
                iterator.remove();
            }
        }
        return new PageRes(p.getTotalPage(), data);
    }

    @RequestMapping("/updateTaskInfo")
    @ResponseBody
    public Response updateTaskInfo(TaskInfo info, HttpServletRequest req) {
        UserInfo userinfo = SessionUtil.getUserinfo(req);
        Integer isLeader = userinfo.getIsLeader();
        if (isLeader == 0 && info.getTaskState() == 4) {  //非组长无法关闭问题
            return Response.NO_PERMISSION;
        }
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        taskInfoMapper.updateByPrimaryKeySelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/addTaskInfo")
    @ResponseBody
    public Response addTaskInfo(TaskInfo info, HttpServletRequest req) {
        UserInfo userinfo = SessionUtil.getUserinfo(req);
        Integer roleID = userinfo.getRoleID();
        Integer isLeader = userinfo.getIsLeader();
        if (isLeader == 0 && roleID != 2) {  //不是组长且不是测试人员 无权限
            return Response.NO_PERMISSION;
        } else if (isLeader == 1 && roleID == 1) { //开发组长
            info.setTaskType(1);
        } else if (isLeader == 1 && roleID == 2) {  //测试组长
            info.setTaskType(2);
        } else if (isLeader == 0 && roleID == 3) {  //测试组员
            info.setTaskType(3);
        }
        Integer taskState = info.getTaskState();
        if (taskState != 0) {
            Response.err("任务状态只能是已建议");
        }
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        taskInfoMapper.insertSelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/deleteTaskInfo")
    @ResponseBody
    public Response deleteTaskInfo(Integer taskID, HttpServletRequest req) {
        UserInfo userinfo = SessionUtil.getUserinfo(req);
        Integer roleID = userinfo.getRoleID();
        if (roleID != 3) {
            return Response.NO_PERMISSION;
        }
        if (taskID == null) {
            return Response.LACK_OF_PARAM;
        }
        int i = taskInfoMapper.deleteByPrimaryKey(taskID);
        if (i == 1) {
            return Response.SUCCESS;
        } else {
            return Response.err("删除失败,请校核");
        }
    }

    @RequestMapping("/getSumInfoProcess")
    @ResponseBody
    public Response getSumInfoProcess(Integer processID) {
        if (processID == null) {
            return Response.LACK_OF_PARAM;
        }
        HashMap<String, Object> map = new HashMap<>();
        List<TaskInfo> list = taskInfoMapper.selectByProcessID(processID);
        ProcessInfo processInfo = processInfoMapper.selectByPrimaryKey(processID);
        int total = list.size();
        map.put("total", total);
        int solved = 0;
        for (TaskInfo task : list) {
            if (task.getTaskState() >= 2) {
                solved++;
            }
        }
        map.put("solved", solved + "");
        map.put("solvedRate", solved * 100 / total + "");
        map.put("currentTaskType", processInfo.getCurrentTaskType());
        map.put("processID", processID);
        map.put("creater", processInfo.getCreateUserID());
        ArrayList<Map<String, Object>> list1 = new ArrayList<>();
        list1.add(map);
        return new PageRes(1, list1);
    }

    @RequestMapping("/getSumInfoUser")
    @ResponseBody
    public Response getSumInfoUser(Integer userID) {
        if (userID == null) {
            return Response.LACK_OF_PARAM;
        }
        HashMap<String, Object> map = new HashMap<>();

        List<TaskInfo> list = taskInfoMapper.selectByUserID(userID);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userID);

        int total = list.size();
        map.put("total", total);
        int solved = 0;
        for (TaskInfo task : list) {
            if (task.getTaskState() >= 2) {
                solved++;
            }
        }
        map.put("solved", solved + "");
        map.put("solvedRate", solved * 100 / total + "");
        map.put("userID", userID);
        map.put("userName", userInfo.getUserName());
        map.put("roleID", userInfo.getRoleID());
        ArrayList<Map<String, Object>> list1 = new ArrayList<>();
        list1.add(map);
        return new PageRes(1, list1);
    }

    @RequestMapping("/getSumInfoState")
    @ResponseBody
    public Response getSumInfoState(Integer taskState) {
        if (taskState == null) {
            return Response.LACK_OF_PARAM;
        }
        HashMap<String, Object> map = new HashMap<>();

        List<TaskInfo> list = taskInfoMapper.selectByTaskType();

        int total = list.size();
        map.put("total", total);
        int require = 0;
        int dev = 0;
        int test = 0;
        int bug = 0;
        int solved = 0;
        for (TaskInfo task : list) {
            if (task.getTaskState() >= 2) {
                solved++;
            }
            switch (task.getTaskType()) {
                case 0:
                    require++;
                    break;
                case 1:
                    dev++;
                    break;
                case 2:
                    test++;
                    break;
                case 3:
                    bug++;
                    break;
            }
        }
        map.put("solved", solved + "");
        map.put("solvedRate", solved * 100 / total + "");
        map.put("require", require + "");
        map.put("dev", dev + "");
        map.put("test", test + "");
        map.put("bug", bug + "");
        ArrayList<Map<String, Object>> list1 = new ArrayList<>();
        list1.add(map);
        return new PageRes(1, list1);
    }
}
