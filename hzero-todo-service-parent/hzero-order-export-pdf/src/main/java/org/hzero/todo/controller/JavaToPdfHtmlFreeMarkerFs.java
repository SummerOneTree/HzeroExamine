package org.hzero.todo.controller;

import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.hzero.todo.util.PathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 引入图片导出
 * @Date 2020/9/16 13:55
 * @Author Summer_OneTree
 */
@Controller
@RequestMapping("/Fs")
public class JavaToPdfHtmlFreeMarkerFs {

    private static final String HTML = "/templates/template_freemarker_fs.html";
    private static final String FONT = "simhei.ttf";
    private static final String LOGO_PATH = "file:/"+ PathUtil.getCurrentPath()+"/logo.png";

    private static Configuration freemarkerCfg = null;

    static {
        freemarkerCfg =new Configuration();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/out")
    public void export(HttpServletResponse response) throws IOException, com.lowagie.text.DocumentException {
        Map<String,Object> data = new HashMap<>();
        data.put("name","鲁家宁");
        String content = JavaToPdfHtmlFreeMarkerFs.freeMarkerRender(data,HTML);
        if (content == null) {
            return;
        }
        JavaToPdfHtmlFreeMarkerFs.createPdf(content, response);
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

    public static void createPdf(String content, HttpServletResponse response) throws IOException, com.lowagie.text.DocumentException {

        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 解析html生成pdf
        render.setDocumentFromString(content);
        //解决图片相对路径的问题
        render.getSharedContext().setBaseURL(LOGO_PATH);
        render.layout();
        render.createPDF(response.getOutputStream());
    }
}
