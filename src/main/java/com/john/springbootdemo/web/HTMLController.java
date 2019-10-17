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
        File downLoadFile = new File(downloadFilePath, fileName);
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
            LogUtil.log("异常信息--> " + e.getMessage());
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


    /**
     * 文件断点下载
     */
    @RequestMapping(value = "/breakPointDownloadPng", method = RequestMethod.GET)
    public void breakPointDownload(HttpServletResponse resp, HttpServletRequest req) {
        String fileName = "downloadtest.png";
        File downLoadFile;
        OutputStream os = null;
        FileInputStream is = null;
        try {
            downLoadFile = new File(downloadFilePath, fileName);
            is = new FileInputStream(downLoadFile);
            long fileSize = downLoadFile.length();
            resp.setHeader("Accept-Ranges", "bytes");
            //			resp.setHeader("Content-Length", fileSize + "");
            String range = req.getHeader("Range");
            LogUtil.log("开始断点传输图片---range->"+range);
            int status = HttpServletResponse.SC_OK; // 返回的状态码，默认200，首次下载
            // 如果range下载区域为空，则首次下载，
            if (range == null) {
                range = "bytes=0-";
            } else {
                // 通过下载区域下载，使用206状态码支持断点续传
                status = HttpServletResponse.SC_PARTIAL_CONTENT;
            }

            long start = 0, end = 0;
            if (range.startsWith("bytes=")) {
                String[] values = range.split("=")[1].split("-");
                start = Integer.parseInt(values[0]);
                // 如果服务器端没有设置end结尾，默认取下载全部
                if (values.length == 1) {
                    end = fileSize;
                } else {
                    end = Integer.parseInt(values[1]);
                }
                LogUtil.log("图片 start:"+start+" 图片end: "+end);
            }
            // 此次数据响应大小
            long responseSize = 0;
            if (end != 0 && end > start) {
//                responseSize = end - start + 1;
                responseSize = end - start ;
                // 返回当前连接下载的数据大小,也就是此次数据传输大小
                resp.addHeader("Content-Length", "" + responseSize);
            } else {
                responseSize = Integer.MAX_VALUE;
            }

            byte[] buffer = new byte[4096];
            // 设置响应状态码
            resp.setStatus(status);
            if (status == HttpServletResponse.SC_PARTIAL_CONTENT) {
                // 设置断点续传的Content-Range传输字节和总字节
                resp.addHeader("Content-Range", "bytes " + start + "-" + (fileSize - 1) + "/" + fileSize);
            }
            // 设置响应客户端内容类型
            resp.setContentType("application/x-download");
            // 设置响应客户端头
            resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 当前需要下载文件的大小
            int needSize = (int) responseSize;
            if (start != 0) {
                // 跳已经传输过的字节
                is.skip(start);
            }
            os = resp.getOutputStream();
            while (needSize > 0) {
                int len = is.read(buffer);
                if (needSize < buffer.length) {
                    os.write(buffer, 0, needSize);
                } else {
                    os.write(buffer, 0, len);
                    // 如果读取文件大小小于缓冲字节大小，表示已写入完，直接跳出
                    if (len < buffer.length) {
                        break;
                    }
                }
                // 不断更新当前可下载文件大小
                needSize -= buffer.length;
            }
            LogUtil.log("png断点传输完成------>");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.log("png断点传输异常信息--> " + e.getMessage());
        } finally {
            close(os, is);
        }

    }

    private void close(OutputStream os, FileInputStream is) {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null)
                os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件断点下载
     */
    @RequestMapping(value = "/breakPointDownloadApk", method = RequestMethod.GET)
    public void breakPointDownloadApk(HttpServletResponse resp, HttpServletRequest req) {
        String fileName = "app-test.apk";
        OutputStream apkOs = null;
        FileInputStream apkIs = null;
        dealFile(resp, req, fileName, apkOs, apkIs);

    }

    /**
     * 文件断点下载
     */
    @RequestMapping(value = "/breakPointDownloadApk2", method = RequestMethod.GET)
    public void breakPointDownloadApk2(HttpServletResponse resp, HttpServletRequest req) {
        String fileName = "app-test2.apk";
        OutputStream apkOs = null;
        FileInputStream apkIs = null;
        dealFile(resp, req, fileName, apkOs, apkIs);

    }

    /**
     * 文件断点下载
     */
    @RequestMapping(value = "/breakPointDownloadApk3", method = RequestMethod.GET)
    public void breakPointDownloadApk3(HttpServletResponse resp, HttpServletRequest req) {
        String fileName = "app-test3.apk";
        OutputStream apkOs = null;
        FileInputStream apkIs = null;
        dealFile(resp, req, fileName, apkOs, apkIs);

    }

    /**
     * 文件断点下载
     */
    @RequestMapping(value = "/breakPointDownloadApk4", method = RequestMethod.GET)
    public void breakPointDownloadApk4(HttpServletResponse resp, HttpServletRequest req) {
        String fileName = "app-test4.apk";
        OutputStream apkOs = null;
        FileInputStream apkIs = null;
        dealFile(resp, req, fileName, apkOs, apkIs);

    }


    private void dealFile(HttpServletResponse resp, HttpServletRequest req, String fileName, OutputStream apkOs, FileInputStream apkIs) {
        File downLoadFile;
        try {
            downLoadFile = new File(downloadFilePath, fileName);
            apkIs = new FileInputStream(downLoadFile);
            long fileSize = downLoadFile.length();
            resp.setHeader("Accept-Ranges", "bytes");
            //			resp.setHeader("Content-Length", fileSize + "");
            String range = req.getHeader("Range");
            LogUtil.log("开始断点传输APK---range->"+range);
            int status = HttpServletResponse.SC_OK; // 返回的状态码，默认200，首次下载
            // 如果range下载区域为空，则首次下载，
            if (range == null) {
                range = "bytes=0-";
            } else {
                // 通过下载区域下载，使用206状态码支持断点续传
                status = HttpServletResponse.SC_PARTIAL_CONTENT;
            }

            long start = 0, end = 0;
            if (range.startsWith("bytes=")) {
                String[] values = range.split("=")[1].split("-");
                start = Integer.parseInt(values[0]);
                // 如果服务器端没有设置end结尾，默认取下载全部
                if (values.length == 1) {
                    end = fileSize;
                } else {
                    end = Integer.parseInt(values[1]);
                }
                LogUtil.log("APK start:"+start+" 图片end: "+end);
            }
            // 此次数据响应大小
            long responseSize = 0;
            if (end != 0 && end > start) {
                //                responseSize = end - start + 1;
                responseSize = end - start ;
                // 返回当前连接下载的数据大小,也就是此次数据传输大小
                resp.addHeader("Content-Length", "" + responseSize);
            } else {
                responseSize = Integer.MAX_VALUE;
            }

            byte[] buffer = new byte[4096];
            // 设置响应状态码
            resp.setStatus(status);
            if (status == HttpServletResponse.SC_PARTIAL_CONTENT) {
                // 设置断点续传的Content-Range传输字节和总字节
                resp.addHeader("Content-Range", "bytes " + start + "-" + (fileSize - 1) + "/" + fileSize);
            }
            // 设置响应客户端内容类型
            resp.setContentType("application/x-download");
            // 设置响应客户端头
            resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 当前需要下载文件的大小
            int needSize = (int) responseSize;
            if (start != 0) {
                // 跳已经传输过的字节
                apkIs.skip(start);
            }
            apkOs = resp.getOutputStream();
            while (needSize > 0) {
                int len = apkIs.read(buffer);
                if (needSize < buffer.length) {
                    apkOs.write(buffer, 0, needSize);
                } else {
                    apkOs.write(buffer, 0, len);
                    // 如果读取文件大小小于缓冲字节大小，表示已写入完，直接跳出
                    if (len < buffer.length) {
                        break;
                    }
                }
                // 不断更新当前可下载文件大小
                needSize -= buffer.length;
            }
            LogUtil.log("apk断点传输完成------>");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.log("apk断点传输异常信息--> " + e.getMessage());
        } finally {
            close(apkOs, apkIs);
        }
    }


}


