package com.summer.springboot.easyexcel.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class DownloadDataTypeConverter implements Converter<Integer> {

   //这个是需要被转换结果或者匹配内容
   private static final String A = "独资";
   private static final String B = "合资";
   private static final String C = "独资或合资";

  //这个接口是返回Java中的对象类型
   @Override
   public Class supportJavaTypeKey() {
      return Integer.class;
   }
  //这个接口是返回到Excel的类型
   @Override
   public CellDataTypeEnum supportExcelTypeKey() {
      return CellDataTypeEnum.STRING;
   }
    /**
     * convertToJavaData 从方法名就能看出来 转换成Java数据，把Excel转换成实体类
     * 解析数据就用到的是这个方法
     * 要是表格内容是 独资 到 实体类 后就变成 Integer类型 1；合资 到 实体类 后就变成 Integer类型 2
     */
   @Override
   public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
      String stringValue = cellData.getStringValue();
      if (A.equals(stringValue)) {
         return 1;
      } else if (B.equals(stringValue)) {
         return 2;
      } else {
         return 3;
      }
   }
    /**
     * 这个方法是我们的主角 convertToExcelData 翻译过来就是 转换到 Excel 数据
     * 和上面的方法类似，只不过反了下，实体类字段的值是 1 就转换成 独资；2 转换成 独资
     */
   @Override
   public CellData convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
      CellData cellData;
      if (integer == 1) {
         cellData = new CellData(A);
      } else if (integer == 2) {
         cellData = new CellData(B);
      } else {
         cellData = new CellData(C);
      }
      return cellData;
   }
}
