package com.hll.test.controller;/**
 * Create by sq598 on 2019/3/12
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.firenio.baseio.common.Cryptos;
import com.hll.test.common.*;
import com.hll.test.common.page.Page;
import com.hll.test.dao.ProcessInfoMapper;
import com.hll.test.dao.UserInfoMapper;
import com.hll.test.dao.domain.ProcessInfo;
import com.hll.test.dao.domain.UserInfo;

@Controller
public class ProcessController {

    @Resource
    private ProcessInfoMapper processInfoMapper;

    @RequestMapping("/getProcessInfoPage")
    @ResponseBody
    public Response getOperatorInfoPage(int page, HttpServletRequest req) {
        UserInfo info = SessionUtil.getUserinfo(req);
        if (info.getIsLeader() != 1) {
            return Response.NO_PERMISSION;
        }
        Page p = new Page(page);
        Map<String, Object> map = new HashMap<>();
        map.put(Page.KEY, p);
        if (info.getRoleID() == 3) {
            map.put("roleID", "");
        } else {
            map.put("roleID", info.getRoleID());
        }
        Util.removeNullEntry(map);
        List data = processInfoMapper.selectAllPage(map);
        return new PageRes(p.getTotalPage(), data);
    }


    @RequestMapping("/updateProcessInfo")
    @ResponseBody
    public Response updateOperatorInfo(ProcessInfo info) {
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        processInfoMapper.updateByPrimaryKeySelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/deleteProcessInfo")
    @ResponseBody
    public Response updateOperatorInfo(Integer processID) {
        if (processID == null) {
            return Response.LACK_OF_PARAM;
        }
        int i = processInfoMapper.deleteByPrimaryKey(processID);
        if (i == 1) {
            return Response.SUCCESS;
        } else {
            return Response.err("删除失败,请校核");
        }
    }

    @RequestMapping("/addProcessInfo")
    @ResponseBody
    public Response addOperatorInfo(ProcessInfo info, HttpServletRequest req) {
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        UserInfo currUser = SessionUtil.getUserinfo(req);
        if (currUser.getRoleID() != 0 && currUser.getRoleID() != 3) {
            return Response.NO_PERMISSION;
        }
        info.setCreateUserID(currUser.getUserID());
        info.setCurrentTaskType(0);  //需求任务
        info.setIsComplete(0);
        processInfoMapper.insertSelective(info);
        return Response.SUCCESS;
    }


}
