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

import com.deepcre.radar.entity.StockoutOrderDetailEntity;
import com.deepcre.radar.service.StockoutOrderDetailService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-17 14:29:13
 */
@RestController
@RequestMapping("generator/stockoutorderdetail")
public class StockoutOrderDetailController {
    @Autowired
    private StockoutOrderDetailService stockoutOrderDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:stockoutorderdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockoutOrderDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:stockoutorderdetail:info")
    public R info(@PathVariable("id") Long id){
		StockoutOrderDetailEntity stockoutOrderDetail = stockoutOrderDetailService.getById(id);

        return R.ok().put("stockoutOrderDetail", stockoutOrderDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:stockoutorderdetail:save")
    public R save(@RequestBody StockoutOrderDetailEntity stockoutOrderDetail){
		stockoutOrderDetailService.save(stockoutOrderDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:stockoutorderdetail:update")
    public R update(@RequestBody StockoutOrderDetailEntity stockoutOrderDetail){
		stockoutOrderDetailService.updateById(stockoutOrderDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:stockoutorderdetail:delete")
    public R delete(@RequestBody Long[] ids){
		stockoutOrderDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
