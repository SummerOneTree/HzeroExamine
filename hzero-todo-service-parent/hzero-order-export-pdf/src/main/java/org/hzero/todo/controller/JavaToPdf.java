package org.hzero.todo.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 将 hello china输出到pdf上
 * @Date 2020/9/16 11:55
 * @Author Summer_OneTree
 */
@Controller
@RequestMapping("/pdf")
public class JavaToPdf {

    @RequestMapping("/out")
    public void export(HttpServletResponse response) throws IOException, DocumentException {
        Document document = new Document();
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("hello china"));
        document.close();
        writer.close();
    }
}