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

import com.deepcre.radar.entity.KeepingAmountEntity;
import com.deepcre.radar.service.KeepingAmountService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/keepingamount")
public class KeepingAmountController {
    @Autowired
    private KeepingAmountService keepingAmountService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:keepingamount:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = keepingAmountService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 产品库存列表
     */
    @RequestMapping("/productlist")
//    @RequiresPermissions("radar:keepingamount:list")
    public R productlist(@RequestParam Map<String, Object> params){
        PageUtils page = keepingAmountService.queryProductPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 半成品库存列表
     */
    @RequestMapping("/semilist")
//    @RequiresPermissions("radar:keepingamount:list")
    public R semilist(@RequestParam Map<String, Object> params){
        PageUtils page = keepingAmountService.querySemiPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 原料库存列表
     */
    @RequestMapping("/materiallist")
//    @RequiresPermissions("radar:keepingamount:list")
    public R materiallist(@RequestParam Map<String, Object> params){
        PageUtils page = keepingAmountService.queryMaterialPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    @RequiresPermissions("radar:keepingamount:info")
    public R info(@PathVariable("catId") Long catId){
		KeepingAmountEntity keepingAmount = keepingAmountService.getById(catId);

        return R.ok().put("keepingAmount", keepingAmount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:keepingamount:save")
    public R save(@RequestBody KeepingAmountEntity keepingAmount){
		keepingAmountService.save(keepingAmount);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:keepingamount:update")
    public R update(@RequestBody KeepingAmountEntity keepingAmount){
		keepingAmountService.updateById(keepingAmount);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:keepingamount:delete")
    public R delete(@RequestBody Long[] catIds){
		keepingAmountService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
