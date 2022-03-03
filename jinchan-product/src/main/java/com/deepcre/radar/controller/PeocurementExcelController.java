package com.deepcre.radar.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.deepcre.common.utils.EasyPoiExcelUtil;
import com.deepcre.radar.service.ProcurementService;
import com.deepcre.radar.vo.ProcurementExcelRespVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author alvis-yiang
 * @create 2022-02-11 11:35 AM
 */
@Controller
@RequestMapping("radar/procurement/excel")
public class PeocurementExcelController {

    @Autowired
    ProcurementService procurementService;

//    @RequestMapping("/export")
//    public void export(HttpServletResponse response, @RequestParam Map<String, Object> params) throws IOException {
//
//        //模拟从数据库获取需要导出的数据
////        List<Person> personList = new ArrayList<>();
////        Person person1 = new Person("路飞","1",new Date());
////        Person person2 = new Person("娜美","2", DateUtils.addDate(new Date(),3));
////        Person person3 = new Person("索隆","1", DateUtils.addDate(new Date(),10));
////        Person person4 = new Person("小狸猫","1", DateUtils.addDate(new Date(),-10));
////        personList.add(person1);
////        personList.add(person2);
////        personList.add(person3);
////        personList.add(person4);
//
//        List list = procurementService.exportExcel(response.getOutputStream(), params);
//
//        //导出操作
//        EasyPoiExcelUtil.exportExcel(list,"唯赞采购需求表","需求表",ProcurementExcelRespVo.class,"唯赞采购需求表.xls",response);
//    }

//    @RequestMapping("/importExcel")
//    public void importExcel(){
//        String filePath = "F:\\海贼王.xls";
//        //解析excel，
//        List<Person> personList = FileUtil.importExcel(filePath,1,1,Person.class);
//        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
//        System.out.println("导入数据一共【"+personList.size()+"】行");
//
//        //TODO 保存数据库
//    }

//    @RequestMapping("/poi")
//    @RequiresPermissions("biz:personbase:save")
//    public void poiInfo(HttpServletResponse response,@RequestBody List<PersonBasePoiForm> personBasePoiForm){
//        //导出操作
//        ExportParams ex = new ExportParams("表头名", "Sheet名称");
//        ex.setStyle(ExcelStyleUtil.class);
//        Workbook workbook = ExcelExportUtil.exportExcel(ex,PersonBasePoiForm.class,personBasePoiForm);
//
//        response.setHeader("content-Type","application/vnd.ms-excel");
//        response.setHeader("Content-Disposition","attachment;filename="+System.currentTimeMillis()+".xls");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            workbook.write(response.getOutputStream());
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
