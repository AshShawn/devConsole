package com.hll.test.controller;/**
 * Create by sq598 on 2019/3/12
 */

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
        List data = userInfoMapper.getUserByRoleID(map);
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
    public Response useLogin(HttpSession session, HttpServletResponse res, String userId, String pass, String verificationCode) {
        if (StringUtil.isNullOrBlank(userId) || StringUtil.isNullOrBlank(pass)) {
            session.setAttribute(Keys.VERIFYCODE_KEY, "0");
            return Response.LACK_OF_PARAM;
        }
        //校核验证码
        if (session.getAttribute(Keys.VERIFYCODE_KEY) != null) {
            String code = SessionUtil.getCode(session);
            if (!code.equalsIgnoreCase(verificationCode)) {
                return Response.VERCODE_ERROR;
            } else {
                session.removeAttribute(Keys.VERIFYCODE_KEY);
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
    public Response updateOperatorInfo(UserInfo info) {
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        userInfoMapper.updateByPrimaryKeySelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/deleteUserInfo")
    @ResponseBody
    public Response updateOperatorInfo(Integer userID) {
        if (userID == null) {
            return Response.LACK_OF_PARAM;
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
        UserInfo currUser = SessionUtil.getUserinfo(req);
        if (currUser.getUserID() != 999) {
            return Response.NO_PERMISSION;
        }
        info.setPassword(Cryptos.getMd5_16("123456"));
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
