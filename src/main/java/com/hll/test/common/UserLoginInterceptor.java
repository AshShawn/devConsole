package com.hll.test.common;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class UserLoginInterceptor implements HandlerInterceptor {


    private Set<String> uncheckUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        if (uncheckUrls.contains(requestUrl)) {
            return true;
        } else {
            String token = request.getParameter("token");
            if (StringUtil.isNullOrBlank(token)) {
                if (!SessionUtil.isLogined(request)) {
                    Util.writeHttpResponse(response, Response.NOT_LOGIN);
                    return false;
                }
            } else {
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

    }

    public Set<String> getUncheckUrls() {
        return uncheckUrls;
    }

    public void setUncheckUrls(Set<String> uncheckUrls) {
        this.uncheckUrls = uncheckUrls;
    }

}
