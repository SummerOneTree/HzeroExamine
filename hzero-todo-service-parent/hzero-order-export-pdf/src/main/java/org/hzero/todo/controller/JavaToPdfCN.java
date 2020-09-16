package org.hzero.todo.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 将 “hello 中国”输出到pdf上，想要输出中文需要在其中加上字体样式
 * @Date 2020/9/16 11:55
 * @Author Summer_OneTree
 */
@Controller
@RequestMapping("/CN")
public class JavaToPdfCN {

    private static final String FONT = "simhei.ttf";


    @RequestMapping("/out")
    public void export(HttpServletResponse response) throws IOException, DocumentException {
        Document document = new Document();
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        document.add(new Paragraph("hello 中国", font));
        document.close();
        writer.close();
    }
}