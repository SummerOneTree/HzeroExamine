package org.hzero.todo.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.hzero.todo.util.PathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地动态参数，html导出pdf，使用FreeMarker
 * @Date 2020/9/16 13:27
 * @Author Summer_OneTree
 */
@Controller
@RequestMapping("/Freemarker")
public class JavaToPdfHtmlFreeMarker {
    private static final String HTML = "/templates/template_freemarker.html";
    private static final String FONT = "simhei.ttf";

    private static Configuration freemarkerCfg = null;
    static {
            freemarkerCfg = new Configuration();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/out")
    public void export(HttpServletResponse response) throws IOException, DocumentException {
        Map<String,Object> data = new HashMap<>();
        data.put("name","鲁家宁");
        String content = JavaToPdfHtmlFreeMarker.freeMarkerRender(data,HTML);
        if (content == null) {
            return;
        }
        JavaToPdfHtmlFreeMarker.createPdf(content, response);
    }

    public static void createPdf(String content, HttpServletResponse response) throws IOException, DocumentException {
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
                new ByteArrayInputStream(content.getBytes()), null, StandardCharsets.UTF_8, fontImp);
        // step 5
        document.close();

    }

    /**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            //将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(data, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
