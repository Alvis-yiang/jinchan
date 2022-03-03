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

import com.deepcre.radar.entity.SaleEntity;
import com.deepcre.radar.service.SaleService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 销售表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("radar:sale:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = saleService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listpage")
//    @RequiresPermissions("radar:sale:list")
    public R listpage(@RequestParam Map<String, Object> params){
        PageUtils page = saleService.queryListPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 销售
     */
    @RequestMapping("/sale")
    public R info(@RequestBody SaleEntity saleEntity){
        saleService.sale(saleEntity);
        return R.ok();
    }




    /**
     * 信息
     */
    @RequestMapping("/info/{saleId}")
    @RequiresPermissions("radar:sale:info")
    public R info(@PathVariable("saleId") Long saleId){
		SaleEntity sale = saleService.getById(saleId);

        return R.ok().put("sale", sale);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:sale:save")
    public R save(@RequestBody SaleEntity sale){
		saleService.save(sale);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:sale:update")
    public R update(@RequestBody SaleEntity sale){
		saleService.updateById(sale);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:sale:delete")
    public R delete(@RequestBody Long[] saleIds){
		saleService.removeByIds(Arrays.asList(saleIds));

        return R.ok();
    }

}
