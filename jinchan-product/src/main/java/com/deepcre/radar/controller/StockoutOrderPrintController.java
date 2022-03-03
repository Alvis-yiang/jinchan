package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.StockoutOrderPrintEntity;
import com.deepcre.radar.service.StockoutOrderPrintService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-22 13:29:09
 */
@RestController
@RequestMapping("radar/stockoutorderprint")
public class StockoutOrderPrintController {
    @Autowired
    private StockoutOrderPrintService stockoutOrderPrintService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:stockoutorderprint:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockoutOrderPrintService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("radar:stockoutorderprint:info")
    public R info(@PathVariable("id") Long id){
		StockoutOrderPrintEntity stockoutOrderPrint = stockoutOrderPrintService.getById(id);

        return R.ok().put("stockoutOrderPrint", stockoutOrderPrint);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:stockoutorderprint:save")
    public R save(@RequestBody StockoutOrderPrintEntity stockoutOrderPrint){
		stockoutOrderPrintService.save(stockoutOrderPrint);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:stockoutorderprint:update")
    public R update(@RequestBody StockoutOrderPrintEntity stockoutOrderPrint){
		stockoutOrderPrintService.updateById(stockoutOrderPrint);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:stockoutorderprint:delete")
    public R delete(@RequestBody Long[] ids){
		stockoutOrderPrintService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
