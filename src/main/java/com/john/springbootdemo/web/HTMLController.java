package com.john.springbootdemo.web;

import com.john.springbootdemo.result.HttpResult;
import com.john.springbootdemo.result.ResultUtil;
import com.john.springbootdemo.util.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/19 20:02
 * <p/>
 * Description:
 */
//访问HTML页面的注解
@Controller
public class HTMLController {

    @Value("${upload_file_dir}")
    private String upLoadFileName;


    @Value("${local_ip}")
    private String ipHost;

    @Value("${download_file_path}")
    private String downloadFilePath;

    @RequestMapping(value = "/success")
    private String getmainPage() {
        return "success";
    }

    //    @RequestMapping(value = "/")
    //    private String welcomePage() {
    //        return "welcome";
    //    }

    @RequestMapping(value = "/uploadimage", method = RequestMethod.GET)
    private String uploadimage() {
        return "uploadimg";
    }

    /**
     * 单文件件上传
     */
    @RequestMapping(value = "/testUploadFile", method = RequestMethod.POST)
    @ResponseBody
    private HttpResult<Map<String, Object>> testUploadFile(HttpServletRequest req,
                                                           MultipartHttpServletRequest multiReq) {
        //获取上传文件的文件名
        String filename = multiReq.getFile("file1").getOriginalFilename();
        System.out.println("filename: " + filename);
        // 截取上传文件的后缀
        String uploadFileSuffix = filename.substring(
                filename.indexOf('.') + 1, filename.length());
        //保存到数据库的文件名
        String saveName = UUID.randomUUID().toString() + "." + uploadFileSuffix;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        File uploadDir = new File(upLoadFileName);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        //        File localUploadFile = new File(uploadDir, saveName);
        File localUploadFile = new File(uploadDir, filename);//用用户的原始名字和后缀
        if (!localUploadFile.exists()) {
            try {
                localUploadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fis = (FileInputStream) multiReq.getFile("file1").getInputStream();
            fos = new FileOutputStream(localUploadFile);
            byte[] temp = new byte[1024];
            int len;
            while ((len = fis.read(temp)) != -1) {
                fos.write(temp, 0, len);
            }
        } catch (IOException e) {
            LogUtil.log("捕获到异常");
            return ResultUtil.errorPromote(e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("url", ipHost + "/images/" + localUploadFile.getName());
        return ResultUtil.retrunData(map);
    }

    /**
     * 多文件上传
     */
    @RequestMapping(value = "testUploadFiles", method = RequestMethod.POST)
    @ResponseBody
    private HttpResult<Map<String, Object>> handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        BufferedOutputStream stream = null;
        File uploadDir = new File(upLoadFileName);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        Map<String, Object> map = new HashMap<>();
        if (files.isEmpty()) {
            return null;
        }
        for (int i = 0; i < files.size(); i++) {
            if (!files.get(i).isEmpty()) {
                File localUploadFile;
                try {
                    String filename = files.get(i).getOriginalFilename();
                    System.out.println("filename: " + filename);
                    //截取上传文件的后缀
                    String uploadFileSuffix = filename.substring(
                            filename.indexOf('.') + 1, filename.length());
                    //保存到数据库的文件名
                    String saveName = UUID.randomUUID().toString() + "." + uploadFileSuffix;
                    localUploadFile = new File(uploadDir, filename);
                    if (!localUploadFile.exists()) {
                        localUploadFile.createNewFile();
                    }
                    stream = new BufferedOutputStream(new FileOutputStream(localUploadFile));
                    byte[] bytes = files.get(i).getBytes();
                    stream.write(bytes, 0, bytes.length);
                } catch (Exception e) {
                    return ResultUtil.errorPromote(e.getMessage());
                } finally {
                    try {
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                map.put("url" + i, ipHost + "/images/" + localUploadFile.getName());
            } else {
                return ResultUtil.errorPromote("接收到空的文件");
            }
        }
        return ResultUtil.retrunData(map);
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void testDownload(HttpServletResponse res) {
        String fileName = "app-test.apk";
        File downLoadFile = new File(downloadFilePath , fileName);
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        res.setContentLengthLong(downLoadFile.length());//设置文件大小信息
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os;
        LogUtil.log("开始写入");
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(downLoadFile));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.log("异常信息--> "+e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LogUtil.log("执行完毕");
    }
}


