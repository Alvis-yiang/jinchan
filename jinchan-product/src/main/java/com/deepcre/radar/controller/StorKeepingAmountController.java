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

import com.deepcre.radar.entity.StorKeepingAmountEntity;
import com.deepcre.radar.service.StorKeepingAmountService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-21 16:10:21
 */
@RestController
@RequestMapping("radar/storkeepingamount")
public class StorKeepingAmountController {
    @Autowired
    private StorKeepingAmountService storKeepingAmountService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:storkeepingamount:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = storKeepingAmountService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("radar:storkeepingamount:info")
    public R info(@PathVariable("id") Long id){
		StorKeepingAmountEntity storKeepingAmount = storKeepingAmountService.getById(id);

        return R.ok().put("storKeepingAmount", storKeepingAmount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:storkeepingamount:save")
    public R save(@RequestBody StorKeepingAmountEntity storKeepingAmount){
		storKeepingAmountService.save(storKeepingAmount);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:storkeepingamount:update")
    public R update(@RequestBody StorKeepingAmountEntity storKeepingAmount){
		storKeepingAmountService.updateById(storKeepingAmount);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:storkeepingamount:delete")
    public R delete(@RequestBody Long[] ids){
		storKeepingAmountService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
