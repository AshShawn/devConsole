package com.hll.test.controller;/**
 * Create by sq598 on 2019/3/12
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hll.test.common.PageRes;
import com.hll.test.common.Response;
import com.hll.test.common.SessionUtil;
import com.hll.test.common.Util;
import com.hll.test.common.page.Page;
import com.hll.test.dao.ReportInfoMapper;
import com.hll.test.dao.TaskInfoMapper;
import com.hll.test.dao.domain.ReportInfo;
import com.hll.test.dao.domain.TaskInfo;
import com.hll.test.dao.domain.UserInfo;

@Controller
public class ReportController {

    @Resource
    private ReportInfoMapper reportInfoMapper;

    @RequestMapping("/getReportInfoPage")
    @ResponseBody
    public Response getOperatorInfoPage(int page, HttpServletRequest req) {
        UserInfo info = SessionUtil.getUserinfo(req);
        Page p = new Page(page);
        Map<String, Object> map = new HashMap<>();
        map.put(Page.KEY, p);
        Util.removeNullEntry(map);
        List data = reportInfoMapper.selectAll_page(map);
        return new PageRes(p.getTotalPage(), data);
    }

    @RequestMapping("/updateReportInfo")
    @ResponseBody
    public Response updateOperatorInfo(ReportInfo info) {
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        reportInfoMapper.updateByPrimaryKeySelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/addReportInfo")
    @ResponseBody
    public Response addOperatorInfo(ReportInfo info, HttpServletRequest req) {
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        UserInfo userinfo = SessionUtil.getUserinfo(req);
        info.setCreateUser(userinfo.getUserID());
        reportInfoMapper.insertSelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/deleteReportInfo")
    @ResponseBody
    public Response deleteTaskInfo(Integer reportID) {
        if (reportID == null) {
            return Response.LACK_OF_PARAM;
        }
        int i = reportInfoMapper.deleteByPrimaryKey(reportID);
        if (i == 1) {
            return Response.SUCCESS;
        } else {
            return Response.err("删除失败,请校核");
        }
    }
}
