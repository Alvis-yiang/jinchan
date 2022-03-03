package com.deepcre.common.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Date;
import java.util.List;


/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 11:35 2022/2/24
 * @Description:
 */

public class PDFReport {

    private final static String REPORT_PATH = "E:\\JavaProjects\\wz_jinchan\\pdf-report\\jinchan-radar";

    private static void exportReport() {
        BaseFont bf;
        Font font = null;
        Font font2 = null;
        try {

            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);//创建字体
            font = new Font(bf, 12);//使用字体
            font2 = new Font(bf, 12, Font.BOLD);//使用字体
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:/2.pdf"));
            document.open();
            Paragraph elements = new Paragraph("常州武进1区飞行报告", font2);
            elements.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(elements);
            Image png = Image.getInstance("E:\\test.png");
            png.setAlignment(Image.ALIGN_CENTER);
            document.add(png);
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

    // 利用模板生成pdf
    public static void fillTemplate() {
        // 模板路径L
        String templatePath = "E:/测试3.pdf";
        // 生成的新文件路径
        String newPDFPath = "E:/ceshi.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            String[] str = {"123456789", "TOP__ONE", "男", "1991-01-01", "130222111133338888", "河北省保定市"};
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                System.out.println(name);
                form.setField(name, str[i++]);
            }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
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

    public void createPDF(){
        String filename = REPORT_PATH+"\\testpdf.pdf";
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document,new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            document.add(new Paragraph("Hello World!"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (DocumentException e){
            e.printStackTrace();
        }finally {
            document.close();
        }
    }

    /**
     * 根据传入的出库数据生成出库单PDF文件
     * 返回文件存放的路径
     * @param data
     */
    public String createStokoutOrderPDF(List data){
        String filename = REPORT_PATH+"\\testpdf.pdf";
        Document document = new Document(new RectangleReadOnly(595F,420F));//横向A5

        try {
            PdfWriter.getInstance(document,new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            //开始制作内容
            PdfPTable table = new PdfPTable(24);//生成一个24列的表格
            PdfPCell cell;
            int size = 15;
            BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font15 = new Font(baseFont,15);
            Font font12 = new Font(baseFont,12);
            Font font10 = new Font(baseFont,10);
            Font font8 = new Font(baseFont,8);
            Font font6 = new Font(baseFont,6);
            //抬头
            cell = new PdfPCell(new Phrase("唯赞仓储系统出库单",font15));
            cell.setColspan(24);
            cell.setFixedHeight(size*1.5f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(15);//隐藏边框
            table.addCell(cell);
            //出库单号
            cell = new PdfPCell(new Phrase("出库单号：",font10));
            cell.setColspan(24);
            cell.setFixedHeight(size*1.5f);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);//水平靠右
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(15);//隐藏边框
            table.addCell(cell);
            //申请和审核信息
            cell = new PdfPCell(new Phrase("申请人",font10));
            cell.setColspan(2);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("",font10));
            cell.setColspan(3);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("申请时间",font10));
            cell.setColspan(3);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("",font10));
            cell.setColspan(4);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("审核人",font10));
            cell.setColspan(2);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("",font10));
            cell.setColspan(3);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("审核时间",font10));
            cell.setColspan(3);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("",font10));
            cell.setColspan(4);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            //空一行
            cell = new PdfPCell(new Phrase("",font6));
            cell.setColspan(24);
            cell.setFixedHeight(size*0.5f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(15);//隐藏边框
            table.addCell(cell);
            //库存品出库信息
            //******************属性名
            cell = new PdfPCell(new Phrase("序号",font10));
            cell.setColspan(2);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("出库品名称",font10));
            cell.setColspan(4);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("型号",font10));
            cell.setColspan(4);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("单位",font10));
            cell.setColspan(3);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("出库数量",font10));
            cell.setColspan(4);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("仓库位置",font10));
            cell.setColspan(4);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("备注",font10));
            cell.setColspan(3);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            table.addCell(cell);
            //******************属性值
            for (int i = 0; i < data.size(); i++) {
                //序号2
                cell = new PdfPCell(new Phrase(String.valueOf(i),font10));
                cell.setColspan(2);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                //出库品名称4
                //型号4
                //单位3
                //出库数量4
                //仓库位置4
                //备注3
            }

            //空一行
            cell = new PdfPCell(new Phrase("",font6));
            cell.setColspan(24);
            cell.setFixedHeight(size*0.5f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(15);//隐藏边框
            table.addCell(cell);
            //签字部分
            cell = new PdfPCell(new Phrase("领料人签名：",font10));
            cell.setColspan(12);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(2);//隐藏下边框
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("管理员签名：",font10));
            cell.setColspan(12);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(2);//隐藏下边框
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("日期：              年    月    日",font10));
            cell.setColspan(12);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(1);//隐藏上边框
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("日期：               年    月    日",font10));
            cell.setColspan(12);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(1);//隐藏上边框
            table.addCell(cell);
            //空一行
            cell = new PdfPCell(new Phrase("",font10));
            cell.setColspan(24);
            cell.setFixedHeight(size);
            cell.setLeading(0,1);
            cell.disableBorderSide(15);//隐藏边框
            table.addCell(cell);
            //底部公司以及电话
            cell = new PdfPCell(new Phrase("杭州唯赞科技有限公司",font10));
            cell.setColspan(12);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(15);//隐藏边框
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("TEL:0571-56396572",font10));
            cell.setColspan(12);
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell.disableBorderSide(15);//隐藏边框
            table.addCell(cell);

            document.add(table);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (DocumentException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return filename;
    }


}
