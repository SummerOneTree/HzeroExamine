package org.hzero.todo.infra.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.hzero.todo.domain.entity.ExcelData;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Date 2020/8/27 9:23
 * @Author Summer_OneTree
 */
@Slf4j
public class PoiExcelUtil {

    /**
     * 导出Excel
     * @param response HttpServletResponse
     * @param data ExcelData
     */
    public static void exportExcel(HttpServletResponse response, ExcelData data) {
        log.info("导出解析开始，fileName:{}", data.getFileName());
        try {
            //实例化XSSFWorkbook
            XSSFWorkbook workbook = new XSSFWorkbook ();
            //创建一个Excel表单，参数为sheet的名字 hodr_so_header
            XSSFSheet sheet = workbook.createSheet(data.getSheetName());
            //设置表头
            setTitle(workbook, sheet, data.getHead());
            //设置单元格并赋值
            setData(workbook, sheet, data.getData());
            for (int i = 0; i < data.getHead().length; i++)
            {
                sheet.setColumnWidth(i, 20 * 256);
            }
            //设置浏览器下载
            setBrowser(response, workbook, data.getFileName());
            log.info("导出解析成功!");
        } catch (Exception e) {
            log.info("导出解析失败!");
            e.printStackTrace();
        }
    }

    /**
     * 设置表头
     * @param workbook 工作簿
     * @param sheet 工作表
     * @param str 表头名称数组
     */
    private static void setTitle(XSSFWorkbook workbook, XSSFSheet sheet, String[] str) {
        try {
            XSSFRow row = sheet.createRow(0);
            // 设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i <= str.length; i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            // 设置为居中加粗,格式化时间格式 createFont： 字体格式
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            // 设置字号
            font.setFontHeightInPoints((short) 11);
            // 设置字体
            font.setFontName("等线");

            style.setFont(font);
            // 格式化时间格式
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            // 创建表头名称
            XSSFCell cell;
            for (int j = 0; j < str.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(str[j]);
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            log.info("导出时设置表头失败！");
            e.printStackTrace();
        }
    }

    /**
     * 表格赋值
     * @param sheet 工作表
     * @param data 数据
     */
    private static void setData(XSSFWorkbook workbook, XSSFSheet sheet, List<String[]> data) {
        XSSFFont font = workbook.createFont();
        CreationHelper helper = workbook.getCreationHelper();
        XSSFCellStyle style = workbook.createCellStyle();
        font.setFontName("等线");
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setDataFormat(helper.createDataFormat().getFormat("yyyy/MM/dd"));
        try {
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                XSSFRow row = sheet.createRow(rowNum);
                for (int j = 0; j < data.get(i).length; j++) {
                    XSSFCell cell = row.createCell(j);
                    if ("null".equals(data.get(i)[j])) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(data.get(i)[j]);
                    }
                    cell.setCellStyle(style);
                }
                rowNum++;
            }
            log.info("表格赋值成功！");
        } catch (Exception e) {
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

    /**
     * 使用浏览器下载
     * @param response HttpServletResponse
     * @param workbook 工作簿
     * @param fileName 文件名
     */
    private static void setBrowser(HttpServletResponse response, XSSFWorkbook workbook, String fileName) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！");
            e.printStackTrace();
        }

    }

    /**
     * 导入
     * @param fileName 文件名
     * @return List<Object[]>
     */
    public static List<Object[]> importExcel(String fileName) {
        log.info("导入解析开始，fileName:{}", fileName);
        try {
            List<Object[]> list = new ArrayList<>();
            InputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[row.getPhysicalNumberOfCells()];
                int index = 0;
                for (Cell cell : row) {
                    if (cell.getCellType().equals(CellType.NUMERIC)) {
                        objects[index] = Double.toString(cell.getNumericCellValue());
                    }
                    if (cell.getCellType().equals(CellType.STRING)) {
                        objects[index] = cell.getStringCellValue();
                    }
                    if (cell.getCellType().equals(CellType.BOOLEAN)) {
                        objects[index] = cell.getBooleanCellValue();
                    }
                    if (cell.getCellType().equals(CellType.ERROR)) {
                        objects[index] = cell.getErrorCellValue();
                    }
                    index++;
                }
                list.add(objects);
            }
            log.info("导入文件解析成功！");
            return list;
        } catch (Exception e) {
            log.info("导入文件解析失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数字转换成日期格式
     * @param day 日期数字
     * @return 返回日期格式
     */
    private static Date getDate(String day) {
        Calendar c = Calendar.getInstance();
        c.set(1900, Calendar.JANUARY, 1);

        double a = Double.parseDouble(day);
        int b = (int) a;

        c.add(Calendar.DATE, b - 2);
        return c.getTime();
    }
    /**
     * util.date转换为sql.date
     * @param date util.date
     * @return  sql.date
     */
    public static java.sql.Date convertDate(Date date) {
        return new java.sql.Date(date.getTime());
    }
    /**
     * 日期转换
     * @param day 日期对象
     * @return 返回 数据库日期格式
     * @throws ParseException ParseException
     */
    public static java.sql.Date getDay(Object day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(day)) {
            String dayInt = (String) day;
            Date date = getDate(dayInt);
            return convertDate(sdf.parse(sdf.format(date)));
        }
        return null;
    }
}
