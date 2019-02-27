package com.john.springbootdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/mainpage")
    private String getmainPage() {
        return "index";
    }

    @RequestMapping(value = "/uploadimage", method = RequestMethod.GET)
    private String uploadimage() {
        return "uploadimg";
    }

    /**
     * 单文件件上传
     */
    @RequestMapping(value = "/testUploadFile", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> testUploadFile(HttpServletRequest req,
                                               MultipartHttpServletRequest multiReq) {
        Map<String, Object> map = new HashMap<>();
        //获取上传文件的路径
        String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
        System.out.println("uploadFlePath:" + uploadFilePath);
        // 截取上传文件的文件名
        FileOutputStream fos = null;
        FileInputStream fis = null;
        File localUploadFile = new File("D:\\UploadFiles\\" + uploadFilePath);
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
            while ((len=fis.read(temp)) != -1) {
                fos.write(temp, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code", 401);
            map.put("msg", "false");
            map.put("data", e.getMessage());
            return map;
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
        map.put("code", 200);
        map.put("msg", "success");
        map.put("data", "上传成功鸭");
        return map;

    }

    /**
     * 多文件上传
     */
    @RequestMapping(value = "testUploadFiles", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        BufferedOutputStream stream = null;
        Map<String, Object> map = new HashMap<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String uploadFilePath = file.getOriginalFilename();
                    System.out.println("uploadFlePath:" + uploadFilePath);
//                    // 截取上传文件的文件名
//                    String uploadFileName = uploadFilePath
//                            .substring(uploadFilePath.lastIndexOf('\\') + 1,
//                                    uploadFilePath.indexOf('.'));
//                    System.out.println("multiReq.getFile()" + uploadFileName);
//                    // 截取上传文件的后缀
//                    String uploadFileSuffix = uploadFilePath.substring(
//                            uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
//                    System.out.println("uploadFileSuffix:" + uploadFileSuffix);
                    File localUploadFile = new File("D:\\UploadFiles\\" + uploadFilePath);
                    if (!localUploadFile.exists()) {
                        try {
                            localUploadFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    stream = new BufferedOutputStream(new FileOutputStream(localUploadFile));
                    byte[] bytes = file.getBytes();
                    stream.write(bytes, 0, bytes.length);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("code", 401);
                    map.put("msg", "false ");
                    map.put("data", e.getMessage());
                    return map;
                } finally {
                    try {
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                map.put("code", 401);
                map.put("msg", "false");
                map.put("data", "上传文件为空");
                return map;
            }
        }
        map.put("code", 200);
        map.put("msg", "success");
        map.put("data", "多文件上传成功鸭");
        return map;
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void testDownload(HttpServletResponse res) {
        String fileName = "upload.jpg";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("d://"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }
}


