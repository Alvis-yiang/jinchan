package com.deepcre.radar.pdfTest;

import com.deepcre.common.utils.PDFReport;
import com.deepcre.radar.utils.PDFUtils;
import com.deepcre.radar.vo.AgreeStockoutDetailInfoRespVo;
import com.deepcre.radar.vo.AgreeStockoutGoodsAndStorInfoRespVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 14:04 2022/2/24
 * @Description:
 */

public class TestPdf {

    @Test
    public void test1(){
        PDFReport pdfReport = new PDFReport();
        pdfReport.createPDF();
    }

    @Test
    public void test2(){
        PDFUtils pdfReport = new PDFUtils();
        AgreeStockoutDetailInfoRespVo data = new AgreeStockoutDetailInfoRespVo();
        List<AgreeStockoutGoodsAndStorInfoRespVo> list = new ArrayList<>();
        AgreeStockoutGoodsAndStorInfoRespVo res = new AgreeStockoutGoodsAndStorInfoRespVo();

        data.setOrderId("WZ202202220dc1da");
        data.setApplyEmpName("admin");
        data.setCreateDate(new Date(122,1,22,16,52,47));
        data.setAuditEmpName("admin");
        data.setAuditDate(new Date(122,1,22, 17,03,44));

        for (int i = 0; i < 16; i++) {
            res.setGoodsCatName("成品");
            res.setGoodsName("喷金划膜仪");
            res.setGoodsModel("XYZ3000");
            res.setUnit("台");
            res.setStorName("A区1号1层");
            res.setAmount(1);
            list.add(res);
        }


        data.setList(list);

        String fileName = pdfReport.createStokoutOrderPDF(data);
        data.setFilePath(fileName);
        System.out.println("生成打印出库单成功"+fileName);
    }


}
