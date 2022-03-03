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

import com.deepcre.radar.entity.ProductSalEntity;
import com.deepcre.radar.service.ProductSalService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 产品销售属性表：一个id代表一个产品，根据产品id确定该产品属于哪种型号的哪种产品。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/productsal")
public class ProductSalController {
    @Autowired
    private ProductSalService productSalService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:productsal:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productSalService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listinfo")
    @RequiresPermissions("radar:productsal:list")
    public R listinfo(@RequestParam Map<String, Object> params){
        PageUtils page = productSalService.queryPageInfo(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{proSalId}")
    @RequiresPermissions("radar:productsal:info")
    public R info(@PathVariable("proSalId") Long proSalId){
		ProductSalEntity productSal = productSalService.getById(proSalId);

        return R.ok().put("productSal", productSal);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:productsal:save")
    public R save(@RequestBody ProductSalEntity productSal){
		productSalService.save(productSal);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:productsal:update")
    public R update(@RequestBody ProductSalEntity productSal){
		productSalService.updateById(productSal);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:productsal:delete")
    public R delete(@RequestBody Long[] proSalIds){
		productSalService.removeByIds(Arrays.asList(proSalIds));

        return R.ok();
    }

}
