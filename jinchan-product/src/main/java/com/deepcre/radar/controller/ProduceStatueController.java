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

import com.deepcre.radar.entity.ProduceStatueEntity;
import com.deepcre.radar.service.ProduceStatueService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 生产状态表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/producestatue")
public class ProduceStatueController {
    @Autowired
    private ProduceStatueService produceStatueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:producestatue:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = produceStatueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{statueId}")
    @RequiresPermissions("radar:producestatue:info")
    public R info(@PathVariable("statueId") Long statueId){
		ProduceStatueEntity produceStatue = produceStatueService.getById(statueId);

        return R.ok().put("produceStatue", produceStatue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:producestatue:save")
    public R save(@RequestBody ProduceStatueEntity produceStatue){
		produceStatueService.save(produceStatue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:producestatue:update")
    public R update(@RequestBody ProduceStatueEntity produceStatue){
		produceStatueService.updateById(produceStatue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:producestatue:delete")
    public R delete(@RequestBody Long[] statueIds){
		produceStatueService.removeByIds(Arrays.asList(statueIds));

        return R.ok();
    }

}
