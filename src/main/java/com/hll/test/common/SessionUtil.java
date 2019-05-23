package com.hll.test.common;/**
 * Create by sq598 on 2019/3/13
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hll.test.dao.domain.UserInfo;

public class SessionUtil {

    private static final String USER_SESSION_KEY = "USER_SESSION_KEY";
    private static final String CODE_SESSION_KEY = "USER_SESSION_KEY";

    public static void putUser(HttpSession session, UserInfo userInfo) {
        session.setAttribute(USER_SESSION_KEY, userInfo);
    }

    public static boolean isAdmin(HttpServletRequest req) {
        return isAdmin(req.getSession());
    }

    public static boolean isAdmin(HttpSession session) {
        UserInfo info = getUserinfo(session);
        return "admin".equals(info.getUserID());
    }

    public static void putCode(HttpSession session, String code) {
        session.setAttribute(CODE_SESSION_KEY, code);
    }

    public static UserInfo getUserinfo(HttpSession session) {
        return (UserInfo) session.getAttribute(USER_SESSION_KEY);
    }

    public static String getCode(HttpSession session) {
        return (String) session.getAttribute(CODE_SESSION_KEY);
    }

    public static boolean isLogined(HttpServletRequest request) {
        return isLogined(request.getSession());
    }

    public static void removeUserinfo(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    public static UserInfo getUserinfo(HttpServletRequest request) {
        return getUserinfo(request.getSession());
    }

    public static boolean isLogined(HttpSession session) {
        return getUserinfo(session) != null;
    }
}
