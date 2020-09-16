package org.hzero.todo.infra.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.hzero.todo.domain.entity.Company;
import org.hzero.todo.domain.entity.Customer;
import org.hzero.todo.domain.entity.Order;
import org.hzero.todo.domain.entity.SoHeader;

import java.math.BigDecimal;
import java.util.List;


/**
 * @Date 2020/9/4 17:45
 * @Author Summer_OneTree
 */
public class ToPdfUtils {

    /**
     * 定义全局的字体静态变量
      */
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    /**
     * 最大宽度
     */
    private static int maxWidth = 520;

    /**
     * 静态代码块
     */
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成PDF文件
     * @param document document
     * @throws Exception
     */
    public void generatePDF(Document document, Order order, List<Order> list) throws Exception {

        SoHeader soHeader = order.getSoHeader();
        Company company = order.getCompany();
        Customer customer = order.getCustomer();

        BigDecimal bigDecimal = new BigDecimal(0);

        for (Order orderForTotalPrice : list) {
            bigDecimal = bigDecimal.add(orderForTotalPrice.getTotalPrice());
        }

        // 段落
        Paragraph paragraph = new Paragraph("·PDF打印", titlefont);
        // 设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setAlignment(0);
        // 设置左缩进
        paragraph.setIndentationLeft(12);
        // 设置右缩进
        paragraph.setIndentationRight(12);
        // 设置首行缩进
        paragraph.setFirstLineIndent(24);
        // 行间距
        paragraph.setLeading(20f);
        // 设置段落上空白
        paragraph.setSpacingBefore(55f);
        // 设置段落下空白
        paragraph.setSpacingAfter(65f);

        // 段落
        Paragraph p3 = new Paragraph("主要", titlefont);
        // 设置文字居中 0靠左   1，居中     2，靠右
        p3.setAlignment(0);
        // 设置左缩进
        p3.setIndentationLeft(12);
        // 设置右缩进
        p3.setIndentationRight(12);
        // 设置首行缩进
        p3.setFirstLineIndent(24);
        // 行间距
        p3.setLeading(20f);
        // 设置段落上空白
        p3.setSpacingBefore(5f);
        // 设置段落下空白
        p3.setSpacingAfter(15f);

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));

        String orderNumber = "订单编号:  " + soHeader.getOrderNumber();
        String companyName = "公司名称:  " + company.getCompanyName();
        String customerName = "客户名称:  " + customer.getCustomerName();
        String orderDate = "订单日期:  " + soHeader.getOrderDate();
        String totalPrice = "订单金额:  " + bigDecimal;
        String orderStatus = "订单状态:  " + soHeader.getOrderStatus();

        // form表单
        Paragraph p_r1 = new Paragraph(orderNumber, textfont);
        p_r1.setSpacingAfter(15f);

        Paragraph p_r2 = new Paragraph(companyName, textfont);
        p_r2.setSpacingAfter(15f);

        Paragraph p_r3 = new Paragraph(customerName, textfont);
        p_r3.setSpacingAfter(15f);

        Paragraph p_r4 = new Paragraph(orderDate, textfont);
        p_r4.setSpacingAfter(15f);

        Paragraph p_r5 = new Paragraph(totalPrice, textfont);
        p_r5.setSpacingAfter(15f);

        Paragraph p_r6 = new Paragraph(orderStatus, textfont);
        p_r6.setSpacingAfter(15f);


        // new Rectangle(300,300,400,400);设置文本框位置
        // llx参数为横坐标起始位置，urx为横坐标终止位置，llx为300，urx为400就是300-400为矩形的宽，纵向相同
        Rectangle r1 = new Rectangle(80,700,280,720);
        // 显示边框
        r1.setBorder(Rectangle.BOX);
        // 设置线条粗细
        r1.setBorderWidth(1f);
        // 设置边框颜色
        r1.setBorderColor(BaseColor.LIGHT_GRAY);

        Rectangle r2 = new Rectangle(80,670,380,690);
        // 显示边框
        r2.setBorder(Rectangle.BOX);
        // 设置线条粗细
        r2.setBorderWidth(1f);
        // 设置边框颜色
        r2.setBorderColor(BaseColor.LIGHT_GRAY);

        Rectangle r3 = new Rectangle(80,640,380,660);
        // 显示边框
        r3.setBorder(Rectangle.BOX);
        // 设置线条粗细
        r3.setBorderWidth(1f);
        // 设置边框颜色
        r3.setBorderColor(BaseColor.LIGHT_GRAY);

        Rectangle r4 = new Rectangle(80,610,180,630);
        // 显示边框
        r4.setBorder(Rectangle.BOX);
        // 设置线条粗细
        r4.setBorderWidth(1f);
        // 设置边框颜色
        r4.setBorderColor(BaseColor.LIGHT_GRAY);

        Rectangle r5 = new Rectangle(80,580,280,600);
        // 显示边框
        r5.setBorder(Rectangle.BOX);
        // 设置线条粗细
        r5.setBorderWidth(1f);
        // 设置边框颜色
        r5.setBorderColor(BaseColor.LIGHT_GRAY);

        Rectangle r6 = new Rectangle(80,550,180,570);
        // 显示边框
        r6.setBorder(Rectangle.BOX);
        // 设置线条粗细
        r6.setBorderWidth(1f);
        // 设置边框颜色
        r6.setBorderColor(BaseColor.LIGHT_GRAY);

        // 表格
        PdfPTable table = createTable(new float[] { 80, 120, 60, 60, 80, 80 });
        table.addCell(createCell("物料编码", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("物料描述", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("产品单位", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("数量", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("销售单价", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("金额", keyfont, Element.ALIGN_CENTER));

        // 表格赋值
        for (Order detailOrder : list) {
            table.addCell(createCell(detailOrder.getItem().getItemCode(), textfont));
            table.addCell(createCell(detailOrder.getItem().getItemDescription(), textfont));
            table.addCell(createCell(detailOrder.getItem().getItemUom(), textfont));
            table.addCell(createCell(detailOrder.getSoLine().getOrderQuantity().toString(), textfont));
            table.addCell(createCell(detailOrder.getSoLine().getUnitSellingPrice().toString(), textfont));
            table.addCell(createCell(detailOrder.getTotalPrice().toString(), textfont));
        }

        document.add(paragraph);
        document.add(p_r1);
        document.add(p_r2);
        document.add(p_r3);
        document.add(p_r4);
        document.add(p_r5);
        document.add(p_r6);
        document.add(p3);
        // 添加form表单
        document.add(r1);
        document.add(r2);
        document.add(r3);
        document.add(r4);
        document.add(r5);
        document.add(r6);

        document.add(p1);
        document.add(table);
    }


/**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     * @param value value
     * @param font font
     * @return PdfPCell
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..）
     * @param value value
     * @param font font
     * @param align align
     * @return PdfPCell
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * @param value value
     * @param font font
     * @param align align
     * @param colspan colspan
     * @return PdfPCell
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * @param value value
     * @param font font
     * @param align align
     * @param colspan colspan
     * @param borderFlag boderFlag
     * @return PdfPCell
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean borderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!borderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     * @param value value
     * @param font font
     * @param align align
     * @param borderWidth borderWidth
     * @param paddingSize paddingSize
     * @param flag flag
     * @return PdfPCell
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     * @param colNumber colNumber
     * @param align align
     * @return PdfPTable
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建指定列宽、列数的表格
     * @param widths widths
     * @return PdfPTable
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建空白的表格
     * @return PdfPTable
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
/**--------------------------创建表格的方法end------------------- ---------*/

}
