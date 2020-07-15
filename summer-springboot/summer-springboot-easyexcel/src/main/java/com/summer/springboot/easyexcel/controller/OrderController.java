package com.summer.springboot.easyexcel.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.summer.springboot.easyexcel.dao.UploadDAO;
import com.summer.springboot.easyexcel.entity.DemoData;
import com.summer.springboot.easyexcel.entity.DownloadData;
import com.summer.springboot.easyexcel.entity.UploadData;
import com.summer.springboot.easyexcel.excel.DemoDataListener;
import com.summer.springboot.easyexcel.excel.UploadDataListener;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/excel")
@Slf4j
public class OrderController {

    @Autowired
    private UploadDAO uploadDAO;
    @GetMapping("/write")
    public Map write() {
        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
        return null;
    }

    @GetMapping("/read")
    public void read() {
        String fileName = "D:\\code\\summer\\summer-springboot\\summer-springboot-easyexcel\\src\\main\\resources\\write1594792442059.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            data.setType(new Random().nextInt(4));
            data.setSex(new Random().nextBoolean());
            list.add(data);
        }
        return list;
    }
    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>1. 创建excel对应的实体对象 参照{@link DownloadData}
     * <p>2. 设置返回的 参数
     * <p>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");


        String name =   String.format("%s_%s%s", "测试——啥手术",sdf.format(new Date()), ".xlsx");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(name, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
    }

    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        return "success";
    }

}
