package org.hzero.todo.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.hzero.todo.util.PathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 本地html静态页面导出pdf
 *
 * @Date 2020/9/16 13:17
 * @Author Summer_OneTree
 */
@Controller
@RequestMapping("/html")
public class JavaToPdfHtml {
    private static final String HTML = PathUtil.getCurrentPath()+"/templates/template.html";
    private static final String FONT = "simhei.ttf";

    @RequestMapping("/out")
    public void export(HttpServletResponse response) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML), null, StandardCharsets.UTF_8, fontImp);
        // step 5
        document.close();
    }
}
