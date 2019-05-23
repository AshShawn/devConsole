package com.hll.test.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hll.test.common.Response;
import com.hll.test.common.VCodeGenerator;


@Controller
public class MainController {

    private Logger          logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/exception")
    @ResponseBody
    public Response exception(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setStatus(500);
            Response res = new Response(500);
            Exception ex = (Exception) request.getAttribute("exception");
            if (ex == null) {
                res.setDesc("未知错误！");
                return res;
            }
            logger.error(ex.getMessage(), ex);
            if (ex instanceof IOException) {
                return null;
            }
            res.setDesc(ex.getMessage());
            return res;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    //主界面
    @RequestMapping({ "/main", "/index" })
    public void main(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }

    @RequestMapping("/gercode")
    public void gercode(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        VCodeGenerator.get().generate(request, response);
    }

}
