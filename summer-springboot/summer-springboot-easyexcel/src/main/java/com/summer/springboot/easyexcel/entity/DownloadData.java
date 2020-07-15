package com.summer.springboot.easyexcel.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.summer.springboot.easyexcel.excel.convert.DownloadDataTypeConverter;

import lombok.Data;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Data
public class DownloadData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    @ExcelProperty(value = "类型", converter = DownloadDataTypeConverter.class)
    private Integer type;
    @ExcelProperty("性别")
    private Boolean sex;
}
