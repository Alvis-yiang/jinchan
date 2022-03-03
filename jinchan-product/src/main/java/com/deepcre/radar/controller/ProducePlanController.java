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

import com.deepcre.radar.entity.ProducePlanEntity;
import com.deepcre.radar.service.ProducePlanService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 生产计划表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/produceplan")
public class ProducePlanController {
    @Autowired
    private ProducePlanService producePlanService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:produceplan:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = producePlanService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{planId}")
    @RequiresPermissions("radar:produceplan:info")
    public R info(@PathVariable("planId") Long planId){
		ProducePlanEntity producePlan = producePlanService.getById(planId);

        return R.ok().put("producePlan", producePlan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:produceplan:save")
    public R save(@RequestBody ProducePlanEntity producePlan){
		producePlanService.save(producePlan);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:produceplan:update")
    public R update(@RequestBody ProducePlanEntity producePlan){
		producePlanService.updateById(producePlan);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:produceplan:delete")
    public R delete(@RequestBody Long[] planIds){
		producePlanService.removeByIds(Arrays.asList(planIds));

        return R.ok();
    }

}
