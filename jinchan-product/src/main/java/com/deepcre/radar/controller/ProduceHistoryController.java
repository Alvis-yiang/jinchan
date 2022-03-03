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

import com.deepcre.radar.entity.ProduceHistoryEntity;
import com.deepcre.radar.service.ProduceHistoryService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 生产记录表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/producehistory")
public class ProduceHistoryController {
    @Autowired
    private ProduceHistoryService produceHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:producehistory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = produceHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{hisId}")
    @RequiresPermissions("radar:producehistory:info")
    public R info(@PathVariable("hisId") Long hisId){
		ProduceHistoryEntity produceHistory = produceHistoryService.getById(hisId);

        return R.ok().put("produceHistory", produceHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:producehistory:save")
    public R save(@RequestBody ProduceHistoryEntity produceHistory){
		produceHistoryService.save(produceHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:producehistory:update")
    public R update(@RequestBody ProduceHistoryEntity produceHistory){
		produceHistoryService.updateById(produceHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:producehistory:delete")
    public R delete(@RequestBody Long[] hisIds){
		produceHistoryService.removeByIds(Arrays.asList(hisIds));

        return R.ok();
    }

}
