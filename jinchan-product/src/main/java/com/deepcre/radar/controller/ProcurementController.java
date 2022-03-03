package com.deepcre.radar.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deepcre.radar.vo.ProcurementExcelRespVo;
import com.deepcre.radar.vo.ProcurementInfoRespVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.deepcre.radar.entity.ProcurementEntity;
import com.deepcre.radar.service.ProcurementService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-09 14:26:10
 */
@RestController
@RequestMapping("radar/procurement")
public class ProcurementController {
    @Autowired
    private ProcurementService procurementService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("radar:procurement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = procurementService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/excel")
//    @RequiresPermissions("radar:procurement:list")
    public R listForExcel(){
        List<ProcurementInfoRespVo> list = procurementService.queryAll();
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{procurementId}")
//    @RequiresPermissions("radar:procurement:info")
    public R info(@PathVariable("procurementId") Long procurementId){
		ProcurementEntity procurement = procurementService.getById(procurementId);

        return R.ok().put("procurement", procurement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("radar:procurement:save")
    public R save(@RequestBody ProcurementEntity procurement){
//		procurementService.save(procurement);
        int result = procurementService.add(procurement);
        if (result==1){
            return R.ok();
        }else {
            return R.error(200,"新增失败！");
        }

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("radar:procurement:update")
    public R update(@RequestBody ProcurementEntity procurement){
		procurementService.updateById(procurement);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:procurement:delete")
    public R delete(@RequestBody Long[] procurementIds){
		procurementService.removeByIds(Arrays.asList(procurementIds));

        return R.ok();
    }

}
