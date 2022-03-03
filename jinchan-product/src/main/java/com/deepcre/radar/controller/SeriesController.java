package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.SeriesEntity;
import com.deepcre.radar.service.SeriesService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 系列表：喷金划膜系列、切条切料系列、压壳系列、读数系列等。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("radar:series:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = seriesService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表：查出所有系列，以及系列下对应的产品，产品下对应的所有型号
     */
    @RequestMapping("/listbytree")
    public R listByTree(@RequestParam Map<String, Object> params){
        List list = seriesService.queryPageByTree(params);
        return R.ok().put("list",list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{seriesId}")
    @RequiresPermissions("radar:series:info")
    public R info(@PathVariable("seriesId") Long seriesId){
		SeriesEntity series = seriesService.getById(seriesId);

        return R.ok().put("data", series);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:series:save")
    public R save(@RequestBody SeriesEntity series){
		seriesService.save(series);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:series:update")
    public R update(@RequestBody SeriesEntity series){
		seriesService.updateById(series);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:series:delete")
    public R delete(@RequestBody Long[] seriesIds){
		seriesService.removeByIds(Arrays.asList(seriesIds));

        return R.ok();
    }

}
