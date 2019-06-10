package com.hll.test.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javafx.scene.input.InputMethodTextRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.firenio.baseio.common.Cryptos;
import com.hll.test.common.*;
import com.hll.test.common.page.Page;
import com.hll.test.dao.UserInfoMapper;
import com.hll.test.dao.domain.UserInfo;

@Controller
public class UserController {

    @Resource
    private UserInfoMapper userInfoMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/getVerificationCode")
    @ResponseBody
    public Response getCode(HttpServletRequest req, HttpServletResponse res) {
        VCodeGenerator.get().generate(req, res);
        return Response.SUCCESS;
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Response getUsercode(HttpServletRequest request) {
        UserInfo userinfo = SessionUtil.getUserinfo(request);
        if (userinfo == null) {
            return null;
        }
        return new Response(userinfo, Code.SUCCESS);
    }

    @RequestMapping("/isUserLogined")
    @ResponseBody
    public boolean isUserLogined(HttpSession session) {
        return SessionUtil.isLogined(session);
    }

    @RequestMapping("/needShowGenCode")
    @ResponseBody
    public boolean needShowGenCode(HttpSession session) {
        return session.getAttribute(Keys.VERIFYCODE_KEY) != null;
    }


    @RequestMapping("/getUserInfoPage")
    @ResponseBody
    public Response getUserInfoPage(int page, HttpServletRequest req) {
        UserInfo info = SessionUtil.getUserinfo(req);
        if (info.getIsLeader() != 1) {
            return Response.NO_PERMISSION;
        }
        Integer roleID = info.getRoleID();
        Page p = new Page(page);
        Map<String, Object> map = new HashMap<>();
        map.put(Page.KEY, p);
        if (roleID == 3) {   //超管
            map.put("roleID", "");
        } else {
            map.put("roleID", roleID);
        }
        Util.removeNullEntry(map);
        List data = userInfoMapper.getUserByRoleID_page(map);
        return new PageRes(p.getTotalPage(), data);
    }

    @RequestMapping("/updatePassWord")
    @ResponseBody
    public Response updatePassWord(HttpServletRequest request, String passwordOld, String passwordNew) {
        if (StringUtil.isNullOrBlank(passwordOld) || StringUtil.isNullOrBlank(passwordNew)) {
            return Response.LACK_OF_PARAM;
        }
        UserInfo info = SessionUtil.getUserinfo(request);
        String old_p = Cryptos.getMd5_16(passwordOld);
        String new_p = Cryptos.getMd5_16(passwordNew);
        int res = userInfoMapper.updatePwd(new_p, old_p, info.getUserID());
        if (res == 0) {
            return Response.err("帐号或密码校验有误!");
        }
        return Response.SUCCESS;
    }

    @RequestMapping("/userLogin")
    @ResponseBody
    public Response useLogin(HttpSession session, HttpServletResponse res,HttpServletRequest req,
                             String userId, String pass, String verificationCode) {
        if (StringUtil.isNullOrBlank(userId) || StringUtil.isNullOrBlank(pass)) {
            session.setAttribute(Keys.VERIFYCODE_KEY, "0");
            return Response.LACK_OF_PARAM;
        }
        //校核验证码
        if (session.getAttribute(Keys.VERIFYCODE_KEY) != null) {
            String code = SessionUtil.getCode(session);
            if (code.equalsIgnoreCase(verificationCode)) {
                session.removeAttribute(Keys.VERIFYCODE_KEY);
            } else {
                return Response.VERCODE_ERROR;
            }
        }
        String md5Pass = Cryptos.getMd5_16(pass);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(Integer.parseInt(userId));
        if (!userInfo.getPassword().equalsIgnoreCase(md5Pass)) {
            session.setAttribute(Keys.VERIFYCODE_KEY, "0");
            return Response.PWD_ERROR;
        }
        userInfo.setPassword(null);
        SessionUtil.putUser(session, userInfo);
        return new Response(userInfo, Code.SUCCESS);
    }


    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public Response updateOperatorInfo(HttpServletRequest req, UserInfo info) {
        UserInfo userinfo = SessionUtil.getUserinfo(req);
        Integer roleID = userinfo.getRoleID();
        if (roleID != 3) {
            return Response.NO_PERMISSION;
        }
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        userInfoMapper.updateByPrimaryKeySelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/deleteUserInfo")
    @ResponseBody
    public Response updateOperatorInfo(HttpServletRequest request, Integer userID) {
        UserInfo userinfo = SessionUtil.getUserinfo(request);
        Integer roleID = userinfo.getRoleID();
        if (roleID != 3) {
            return Response.NO_PERMISSION;//不是超级管理员无权限删除用户信息
        }
        if (userID == null) {
            return Response.LACK_OF_PARAM;//缺少必填参数
        }
        int i = userInfoMapper.deleteByPrimaryKey(userID);
        if (i == 1) {
            return Response.SUCCESS;
        } else {
            return Response.err("删除失败,请校核");
        }
    }

    @RequestMapping("/addUserInfo")
    @ResponseBody
    public Response addOperatorInfo(UserInfo info, HttpServletRequest req) {
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        UserInfo currUser = SessionUtil.getUserinfo(req); //获取当前用户
        if (currUser.getRoleID() !=3) {
            return Response.NO_PERMISSION;//无权限
        }
        if(userInfoMapper.selectByPrimaryKey(info.getUserID())!=null){
            //不可以插入  前面已经有过了
            return Response.err("用户id已存在");
        }
        info.setPassword(Cryptos.getMd5_16("123456"));//默认密码为123456
        userInfoMapper.insertSelective(info);
        return Response.SUCCESS;
    }

    /**
     * 退出系统
     */
    @RequestMapping("/userLogout")
    @ResponseBody
    public Response userLogout(HttpSession session) {
        SessionUtil.removeUserinfo(session);
        return Response.SUCCESS;
    }

}
