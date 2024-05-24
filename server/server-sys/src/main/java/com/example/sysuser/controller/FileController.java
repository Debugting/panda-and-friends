package com.example.sysuser.controller;

import com.example.common.annotation.Pass;
import com.example.common.bean.DataRes;
import com.example.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class FileController {

    @Value("${filePath}")
    private String filePath;

    /**
     * 上传图片
     */
    @RequestMapping("fileImage/upload")
    @ResponseBody
    @Pass
    public DataRes uploadImage(@RequestParam("file") MultipartFile uploadfile) throws IOException {
        // 获得文件：
        String filename = uploadfile.getOriginalFilename();
        File p = new File(filePath + "/image/" + DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/"));
        if (!p.exists()) {
            p.mkdirs();
        }
        File file = new File(p, DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
        uploadfile.transferTo(file);
        return DataRes.success(DateUtils.formatDateByPattern(new Date(), "yyyy/MM/dd/") + DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
    }

    /**
     * 上传图片
     */
    @RequestMapping("fileImage/upload2")
    @ResponseBody
    @Pass
    public Object uploadImage2(@RequestParam("file") MultipartFile uploadfile) throws IOException {
        // 获得文件：
        String filename = uploadfile.getOriginalFilename();
        Date date = new Date();
        File p = new File(filePath + "/image/" + DateUtils.formatDateByPattern(date, "/yyyy/MM/dd/"));
        if (!p.exists()) {
            p.mkdirs();
        }
        File file = new File(p, DateUtils.formatDateByPattern(date, "yyyyMMddHHmmss") + filename);
        uploadfile.transferTo(file);
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map.put("code", 0);    //0表示上传成功
        map.put("msg", "上传成功"); //提示消息
        map2.put("src", "/fileImage/watch?path=" + DateUtils.formatDateByPattern(date, "yyyy/MM/dd/") + DateUtils.formatDateByPattern(date, "yyyyMMddHHmmss") + filename);
        map2.put("title", filename);
        map.put("data", map2);
        return map;
    }

    /**
     * 查看图片
     */
    @RequestMapping("fileImage/watch")
    @Pass
    public void watch(String path, HttpServletResponse response) throws IOException {
        File file = new File(filePath + "/image/" + path);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            int len;
            byte[] buff = new byte[1204];
            while ((len = fileInputStream.read(buff)) != -1) {
                response.getOutputStream().write(buff, 0, len);
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    /**
     * 上传音频
     */
    @RequestMapping("fileAudio/upload")
    @ResponseBody
    @Pass
    public DataRes uploadAudio(@RequestParam("file") MultipartFile uploadfile) throws IOException {
        // 获得文件：
        String filename = uploadfile.getOriginalFilename();
        File p = new File(filePath + "/audio/" + DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/"));
        if (!p.exists()) {
            p.mkdirs();
        }
        File file = new File(p, DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
        uploadfile.transferTo(file);
        return DataRes.success(DateUtils.formatDateByPattern(new Date(), "yyyy/MM/dd/") + DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
    }

    /**
     * 上传音频
     */
    @RequestMapping("fileAudio/upload2")
    @ResponseBody
    @Pass
    public Object uploadAudio2(@RequestParam("file") MultipartFile uploadfile) throws IOException {
        // 获得文件：
        String filename = uploadfile.getOriginalFilename();
        Date date = new Date();
        File p = new File(filePath + "/audio/" + DateUtils.formatDateByPattern(date, "/yyyy/MM/dd/"));
        if (!p.exists()) {
            p.mkdirs();
        }
        File file = new File(p, DateUtils.formatDateByPattern(date, "yyyyMMddHHmmss") + filename);
        uploadfile.transferTo(file);
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map.put("code", 0);    //0表示上传成功
        map.put("msg", "上传成功"); //提示消息
        map2.put("src", "/fileAudio/watch?path=" + DateUtils.formatDateByPattern(date, "yyyy/MM/dd/") + DateUtils.formatDateByPattern(date, "yyyyMMddHHmmss") + filename);
        map2.put("title", filename);
        map.put("data", map2);
        return map;
    }

    /**
     * 播放音频
     */
    @RequestMapping("fileAudio/watch")
    @Pass
    public void watchAudio(String path, HttpServletResponse response) throws IOException {
        File file = new File(filePath + "/audio/" + path);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            int len;
            byte[] buff = new byte[1204];
            while ((len = fileInputStream.read(buff)) != -1) {
                response.getOutputStream().write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    /**
     * 上传视频
     */
    @RequestMapping("fileVideo/upload")
    @ResponseBody
    @Pass
    public DataRes uploadVideo(@RequestParam("file") MultipartFile uploadfile) throws IOException {
        // 获得文件：
        String filename = uploadfile.getOriginalFilename();
        File p = new File(filePath + "/video/" + DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/"));
        if (!p.exists()) {
            p.mkdirs();
        }
        File file = new File(p, DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
        uploadfile.transferTo(file);
        return DataRes.success(DateUtils.formatDateByPattern(new Date(), "yyyy/MM/dd/") + DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
    }

    /**
     * 上传视频
     */
    @RequestMapping("fileVideo/upload2")
    @ResponseBody
    @Pass
    public Object uploadVideo2(@RequestParam("file") MultipartFile uploadfile) throws IOException {
        // 获得文件：
        String filename = uploadfile.getOriginalFilename();
        Date date = new Date();
        File p = new File(filePath + "/video/" + DateUtils.formatDateByPattern(date, "/yyyy/MM/dd/"));
        if (!p.exists()) {
            p.mkdirs();
        }
        File file = new File(p, DateUtils.formatDateByPattern(date, "yyyyMMddHHmmss") + filename);
        uploadfile.transferTo(file);
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map.put("code", 0);    //0表示上传成功
        map.put("msg", "上传成功"); //提示消息
        map2.put("src", "/fileVideo/watch?path=" + DateUtils.formatDateByPattern(date, "yyyy/MM/dd/") + DateUtils.formatDateByPattern(date, "yyyyMMddHHmmss") + filename);
        map2.put("title", filename);
        map.put("data", map2);
        return map;
    }

    /**
     * 播放视频
     */
    @RequestMapping("fileVideo/watch")
    @Pass
    public void watchVideo(String path, HttpServletResponse response) {
        String file = filePath + "/video/" + path;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            String diskfilename = "final.mp4";
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"");
            System.out.println("data.length " + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            OutputStream os = response.getOutputStream();

            os.write(data);
            //先声明的流后关掉！
            os.flush();
            os.close();
            inputStream.close();

        } catch (Exception ignored) {
        }
    }

    /**
     * 读取json文件
     */
    @ResponseBody
    @RequestMapping("loadJson")
    @Pass
    public DataRes loadJson(String name, HttpServletRequest request, HttpServletResponse response) {
        String file = filePath + "json" + File.separator + name;
        File jsonFile = new File(file);
        try {
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            return DataRes.success(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DataRes.error();
    }


}
