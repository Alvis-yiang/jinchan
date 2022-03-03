package com.deepcre.radar.utils;

import com.deepcre.radar.vo.AgreeStockoutDetailInfoRespVo;
import com.deepcre.radar.vo.AgreeStockoutGoodsAndStorInfoRespVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 17:24 2022/2/24
 * @Description:
 */

public class PDFUtils {

    @Value("${path.pdf.stockoutOrderPath}")
    private String REPORT_PATH;
//    private final static String REPORT_PATH = "E:\\JavaProjects\\wz_jinchan\\files\\pdf-report\\jinchan-radar";

    /**
     * 根据传入的出库数据生成出库单PDF文件
     * 返回文件存放的路径
     * @param data
     */
    public String createStokoutOrderPDF(AgreeStockoutDetailInfoRespVo data){
        String filename = REPORT_PATH+data.getOrderId()+".pdf";
        Document document = new Document(new RectangleReadOnly(595F,420F));//横向A5

        try {
            PdfWriter.getInstance(document,new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            int page = 0;//出库单的页数
            if(data.getList().size()%8==0){
                page = data.getList().size()/8;
            }else {
                page = data.getList().size()/8+1;
            }
            //开始制作内容
            PdfPTable table = new PdfPTable(24);//生成一个24列的表格
            for (int curPage = 1; curPage <= page; curPage++) {
                table.setWidthPercentage (100);//设置表格宽度
                PdfPCell cell;
                int size = 20;
                BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                Font font15 = new Font(baseFont,15, Font.BOLD);
                Font font12 = new Font(baseFont,12);
                Font font10 = new Font(baseFont,10);
                Font font10b = new Font(baseFont, 10,Font.BOLD);
                Font font8 = new Font(baseFont,8);
                Font font8b = new Font(baseFont,8,Font.BOLD);
                Font font6 = new Font(baseFont,6);
                //抬头
                cell = new PdfPCell(new Phrase("仓储系统出库单",font15));
                cell.setColspan(24);
                cell.setFixedHeight(size*1.5f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(15);//隐藏边框
                table.addCell(cell);
                //出库单号
                cell = new PdfPCell(new Phrase("出库单号："+data.getOrderId(),font10b));
                cell.setColspan(24);
                cell.setFixedHeight(size*1.5f);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);//水平靠右
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(15);//隐藏边框
                table.addCell(cell);
                //申请和审核信息
                cell = new PdfPCell(new Phrase("申请人",font10b));
                cell.setColspan(2);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(data.getApplyEmpName(),font10));
                cell.setColspan(3);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("申请时间",font10b));
                cell.setColspan(3);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                cell = new PdfPCell(new Phrase(df.format(data.getCreateDate()),font10));
                cell.setColspan(4);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("审核人",font10b));
                cell.setColspan(2);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(data.getAuditEmpName(),font10));
                cell.setColspan(3);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("审核时间",font10b));
                cell.setColspan(3);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(df.format(data.getAuditDate()),font10));
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
                cell = new PdfPCell(new Phrase("序号",font10b));
                cell.setColspan(2);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("出库品名称",font10b));
                cell.setColspan(4);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("型号",font10b));
                cell.setColspan(4);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("单位",font10b));
                cell.setColspan(3);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("出库数量",font10b));
                cell.setColspan(4);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("仓库位置",font10b));
                cell.setColspan(4);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("备注",font10b));
                cell.setColspan(3);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table.addCell(cell);
                //******************属性值
//                System.out.println("数据："+data.getList());
                int num=0;
                if (curPage<page){
                    num=8;
                }else{
                    num=data.getList().size()-8*(page-1);
                }
                for (int i = 1; i <= num; i++) {
                    AgreeStockoutGoodsAndStorInfoRespVo res = data.getList().get(i-1);
                    //序号2
                    int no = 8*(curPage-1)+i;
                    cell = new PdfPCell(new Phrase(String.valueOf(no),font10));
                    cell.setColspan(2);
                    cell.setFixedHeight(size);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                    table.addCell(cell);
                    //出库品名称4
                    cell = new PdfPCell(new Phrase(res.getGoodsName(),font10));
                    cell.setColspan(4);
                    cell.setFixedHeight(size);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                    table.addCell(cell);
                    //型号4
                    cell = new PdfPCell(new Phrase(res.getGoodsModel(),font10));
                    cell.setColspan(4);
                    cell.setFixedHeight(size);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                    table.addCell(cell);
                    //单位3
                    cell = new PdfPCell(new Phrase(res.getUnit(),font10));
                    cell.setColspan(3);
                    cell.setFixedHeight(size);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                    table.addCell(cell);
                    //出库数量4
                    cell = new PdfPCell(new Phrase(String.valueOf(res.getAmount()),font10));
                    cell.setColspan(4);
                    cell.setFixedHeight(size);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                    table.addCell(cell);
                    //仓库位置4
                    cell = new PdfPCell(new Phrase(res.getStorName(),font10));
                    cell.setColspan(4);
                    cell.setFixedHeight(size);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                    table.addCell(cell);
                    //备注3
                    cell = new PdfPCell(new Phrase("",font10));
                    cell.setColspan(3);
                    cell.setFixedHeight(size);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                    table.addCell(cell);
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
                cell = new PdfPCell(new Phrase("领料人签名：",font10b));
                cell.setColspan(12);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(2);//隐藏下边框
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("管理员签名：",font10b));
                cell.setColspan(12);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(2);//隐藏下边框
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("日期：              年    月    日",font10b));
                cell.setColspan(12);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(1);//隐藏上边框
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("日期：               年    月    日",font10b));
                cell.setColspan(12);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);//靠左
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(1);//隐藏上边框
                table.addCell(cell);
                //底部公司以及电话
                cell = new PdfPCell(new Phrase("杭州唯赞科技有限公司",font8b));
                cell.setColspan(8);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(15);//隐藏边框
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("TEL:0571-56396572",font8b));
                cell.setColspan(8);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(15);//隐藏边框
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("网址：www.deepcre.com",font8b));
                cell.setColspan(8);
                cell.setFixedHeight(size);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell.disableBorderSide(15);//隐藏边框
                table.addCell(cell);
                document.newPage();
            }
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
