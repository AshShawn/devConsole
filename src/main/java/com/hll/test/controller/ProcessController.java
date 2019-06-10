package com.hll.test.controller;/**
 * Create by sq598 on 2019/3/12
 */

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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
            return Response.NO_PERMISSION;//不止组长和管理员无权限打开菜单
        }
        Page p = new Page(page);
        Map<String, Object> map = new HashMap<>();
        map.put(Page.KEY, p);
        if (info.getRoleID() == 3) {
            map.put("roleID", "");//管理员可以查看所有用户
        } else {
            map.put("roleID", info.getRoleID());//组长只能看到组员用户
        }
        Util.removeNullEntry(map);
        List data = processInfoMapper.selectAll_page(map);
        return new PageRes(p.getTotalPage(), data);
    }


    @RequestMapping("/updateProcessInfo")
    @ResponseBody
    public Response updateOperatorInfo(ProcessInfo info, HttpServletRequest request) {
        UserInfo userinfo = SessionUtil.getUserinfo(request);
        Integer isLeader = userinfo.getIsLeader();
        if (isLeader == 0) {
            return Response.NO_PERMISSION;
        }
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        processInfoMapper.updateByPrimaryKeySelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/deleteProcessInfo")
    @ResponseBody
    public Response updateOperatorInfo(Integer processID, HttpServletRequest request) {
        UserInfo userinfo = SessionUtil.getUserinfo(request);
        if (userinfo.getRoleID() != 3) {
            return Response.NO_PERMISSION;
        }
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
        UserInfo currUser = SessionUtil.getUserinfo(req);
        if (currUser.getRoleID() != 0 && currUser.getRoleID() != 3) {
            return Response.NO_PERMISSION;
        }
        if (info == null) {
            return Response.LACK_OF_PARAM;
        }
        if (info.getCurrentTaskType() != 0) {
            return Response.err("当前只能创建需求任务");
        }
        if (info.getDevUserID() == null) {
            return Response.err("必须指定开发人员");
        }
        info.setCreateUserID(currUser.getUserID());
        info.setCurrentTaskType(0);  //需求任务
        info.setIsComplete(0);
        processInfoMapper.insertSelective(info);
        return Response.SUCCESS;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Response upload(Integer processID, HttpServletRequest request) throws IOException {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(3 * 1024 * 1024);  //3mb
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            MultipartFile file = null;
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            multiRequest.setCharacterEncoding("UTF-8");
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //一次遍历所有文件
                file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String path = System.getProperty("user.dir") + "\\src\\main\\webapp\\upload\\" + file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }
            }
            ProcessInfo processInfo = processInfoMapper.selectByPrimaryKey(processID);
            String fileUrl = processInfo.getFileUrl();
            if (StringUtil.isNullOrBlank(fileUrl)) {
                processInfo.setFileUrl(file.getOriginalFilename() + ";");
            } else {
                processInfo.setFileUrl(fileUrl + file.getOriginalFilename()+";");
            }
            processInfoMapper.updateByPrimaryKeySelective(processInfo);
        } else {
            return Response.err("上传失败,请查看enctype类型");
        }
        return Response.SUCCESS;
    }


    @RequestMapping("/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String downloadFilePath = System.getProperty("user.dir") + "\\src\\main\\webapp\\upload\\";//从我们的上传文件夹中去取

        File file = new File(downloadFilePath + filename);//新建一个文件

        HttpHeaders headers = new HttpHeaders();//http头信息

        String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");//设置编码

        headers.setContentDispositionFormData("attachment", downloadFileName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @RequestMapping("/download2")
    @ResponseBody
    public ResponseEntity<byte[]> download2(String filenames, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //需要压缩的文件
        List<String> list = new ArrayList<String>();
        String[] split = filenames.split(";");
        for (String s : split) {
            if (StringUtil.isNullOrBlank(s)){
                continue;
            }
            list.add(s);
        }

        //压缩后的文件
        String resourcesName = System.currentTimeMillis()+".zip";

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\main\\webapp\\download\\"+resourcesName));
        InputStream input = null;

        for (String str : list) {
            String name = System.getProperty("user.dir") + "\\src\\main\\webapp\\upload\\"+str;
            input = new FileInputStream(new File(name));
            zipOut.putNextEntry(new ZipEntry(str));
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
            input.close();
        }
        zipOut.close();
        File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\download\\"+resourcesName);
        HttpHeaders headers = new HttpHeaders();
        String filename = new String(resourcesName.getBytes("utf-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
    }


}
