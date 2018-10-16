package com.school.management.api.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class FileUtils {

    private static final Gson GSON = new Gson();
    private static final HSSFWorkbook workbook = new HSSFWorkbook();

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
     * @param data     数据
     */
    public static void createExcel(String fileName, Collection data, Class clazz, List<Map<String, Object>> header) {
        HSSFSheet sheet = workbook.createSheet();
        try {
            /*
             * 创建表格文件
             */
            File file = new File("E:\\class card\\files\\" + fileName + ".xls");
            if (!file.exists()) {
                file.getParentFile().mkdir();
            }


            /*
             * 创建表头
             */
            createHeader(clazz, sheet, handler(header));

            /*
             * 填充数据
             */
            fillData(sheet, GSON.fromJson(GSON.toJson(data), new TypeToken<List<Map<String, Object>>>() {
            }.getType()));
            if (file.createNewFile()) {
                workbook.write(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createHeader(Class clazz, HSSFSheet sheet, Map<String, String> headerOfCN) {
        HSSFRow header = sheet.createRow(0);
        List<String> fields = getField(clazz);
        for (int i = 0; i < fields.size(); i++) {
            HSSFCell cell = header.createCell(i);
            String name = fields.get(i);
            for (String key : headerOfCN.keySet()) {
                if ((name.toLowerCase()).contains(key.toLowerCase()) || (key.toLowerCase()).contains(name.toLowerCase())) {
                    String value = headerOfCN.get(name);
                    HSSFCellStyle style = workbook.createCellStyle();
                    style.setAlignment(HorizontalAlignment.CENTER);
                    sheet.autoSizeColumn(i, true);
                    sheet.setColumnWidth(0, 20 * 256);
                    cell.setCellValue(value);
                    cell.setCellStyle(style);
                }
            }
        }
    }

    private static void fillData(HSSFSheet sheet, List<Map<String, Object>> datas) {
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> data = datas.get(i);
            Iterator<String> it = data.keySet().iterator();
            HSSFRow row = sheet.createRow(i + 1);
            int j = 0;
            while (it.hasNext()) {
                String key = it.next();
                Object value = data.get(key);
                HSSFCell cell = row.createCell(j);
                HSSFCellStyle style = workbook.createCellStyle();
                style.setAlignment(HorizontalAlignment.CENTER);
                sheet.autoSizeColumn(j, true);
                sheet.setColumnWidth(0, 20 * 256);
                row.setRowStyle(style);
                cell.setCellStyle(style);
                if (!value.toString().startsWith("[")) {
                    String val = value.toString();
                    cell.setCellValue((!val.equals("")) ? val : "暂无数据");
                    j++;
                }
            }
        }
    }

    private static Map<String, String> handler(List<Map<String, Object>> header) {
        Map<String, String> head = new HashMap<>();
        for (Map<String, Object> map : header) {
            for (String key : map.keySet()) {
                if (key.equals("columnName")) {
                    String[] keies = map.get(key).toString().split("_");
                    StringBuffer str = new StringBuffer();
                    for (int i = 0; i < keies.length; i++) {
                        String s = keies[i];
                        if (i > 0 && i < 2) {
                            keies[i] = s.substring(0, 1).toUpperCase() + s.substring(1);
                        } else if (i == 2) {
                            keies[i] = s.substring(0, 1).toUpperCase() + s.substring(1);
                        }
                        str.append(keies[i]);
                    }
                    head.put(str.toString(), map.get("columnComment").toString());
                }
            }
        }
        return head;
    }

    private static List<String> getField(Class clazz) {
        List<String> fieldList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getType().getName();
            if (!name.equals("java.util.List")) {
                fieldList.add(field.getName());
            }
        }
        return fieldList;
    }

    public static List readExcel(File file) {
        List<Object> list = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            Iterator<Sheet> it = workbook.sheetIterator();
            while (it.hasNext()) {
                Sheet sheet = it.next();
                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        System.out.println(cell);
                    }
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        return list;
    }
}
