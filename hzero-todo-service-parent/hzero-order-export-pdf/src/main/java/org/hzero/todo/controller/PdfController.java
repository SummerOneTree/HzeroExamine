package org.hzero.todo.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * @Date 2020/9/16 9:36
 * @Author Summer_OneTree
 */
@Controller
public class PdfController {

    /**
     * 简单导出测试
     * @param response HttpServletResponse
     */
    @RequestMapping("/out")
    private static void exportReport(HttpServletResponse response) {
        BaseFont bf;
        Font font = null;
        Font font2 = null;
        try {
            //创建字体
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
            //使用字体
            font = new Font(bf, 12);
            //使用字体
            font2 = new Font(bf, 12, Font.BOLD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document document = new Document();
        try {
            response.setContentType("application/pdf");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            Paragraph elements = new Paragraph("常州武进1区飞行报告", font2);
            elements.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(elements);
            document.add(new Paragraph("任务编号：20190701        开始日期：20190701", font));
            document.add(new Paragraph("任务名称：常州武进1区     结束日期：20190701", font));
            document.add(new Paragraph("平均飞行高度：100m        平均飞行速度：100km/h", font));
            document.add(new Paragraph("任务面积：1000㎡      结束日期：20190701", font));
            document.add(new Paragraph("飞行总时长：1000㎡", font));
            document.addCreationDate();

            document.close();
        } catch (Exception e) {
            System.out.println("file create exception");
        }
    }
    @RequestMapping("/out2")
    public static void fillTemplate() {
// 模板路径
        String templatePath = "C:\\Users\\Summer_OneTree\\Desktop\\order.pdf";
// 生成的新文件路径
        String newPDFPath = "C:\\Users\\Summer_OneTree\\Desktop\\ceshi.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            // 输出流
            out = new FileOutputStream(newPDFPath);
            // 读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            String[] str = {"123456789", "TOP__ONE", "男", "1991-01-01", "130222111133338888", "河北省保定市"};
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                System.out.println(name);
                form.setField(name, str[i++]);
            }
            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
        } catch (IOException e) {
            System.out.println(1);
        } catch (DocumentException e) {
            System.out.println(2);
        }
    }

}
