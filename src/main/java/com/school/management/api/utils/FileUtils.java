package com.school.management.api.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class FileUtils {

    private static final Gson GSON = new Gson();

    /**
     * 常用组件：
     * HSSFWorkbook                      excel的文档对象
     * HSSFSheet                         excel的表单
     * HSSFRow                           excel的行
     * HSSFCell                          excel的格子单元
     * HSSFFont                          excel字体
     * HSSFDataFormat                    日期格式
     * HSSFHeader                        sheet头
     * HSSFFooter                        sheet尾（只有打印的时候才能看到效果）
     * 样式：
     * HSSFCellStyle                       cell样式
     * 辅助操作包括：
     * HSSFDateUtil                        日期
     * HSSFPrintSetup                      打印
     * HSSFErrorConstants                  错误信息表
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param data     数据
     */
    public static void createExcel(String fileName, String filePath, Collection data) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        /*
         * 创建表头
         */
        createHeader(data.getClass(), sheet);
        List<Map<String, Object>> mapList = GSON.fromJson(GSON.toJson(data), new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        /*
         * 填充数据
         */
        fillData(sheet, mapList);
    }

    private static void createHeader(Class clazz, HSSFSheet sheet) {
        HSSFRow header = sheet.createRow(1);
        List<String> fields = getField(clazz);
        for (int i = 0; i < fields.size(); i++) {
            HSSFCell cell = header.createCell(i);
            cell.setCellValue(fields.get(i));
        }
    }

    private static void fillData(HSSFSheet sheet, List<Map<String, Object>> datas) {
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> data = datas.get(i);
            Iterator<String> it = data.keySet().iterator();
            HSSFRow row = sheet.createRow(i+1);
            int j = 1;
            while (it.hasNext()) {
                Object value = data.get(it.next());
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(value.toString());
            }
        }
    }

    private static List<String> getField(Class clazz) {
        List<String> fieldList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldList.add(field.getName());
        }
        return fieldList;
    }


}
