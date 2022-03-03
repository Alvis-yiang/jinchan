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

import com.deepcre.radar.entity.SemiMatRelationEntity;
import com.deepcre.radar.service.SemiMatRelationService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 半成品原料关系表：一种半成品对应多种原料
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/semimatrelation")
public class SemiMatRelationController {
    @Autowired
    private SemiMatRelationService semiMatRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:semimatrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = semiMatRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{relId}")
    @RequiresPermissions("radar:semimatrelation:info")
    public R info(@PathVariable("relId") Long relId){
		SemiMatRelationEntity semiMatRelation = semiMatRelationService.getById(relId);

        return R.ok().put("semiMatRelation", semiMatRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:semimatrelation:save")
    public R save(@RequestBody SemiMatRelationEntity semiMatRelation){
		semiMatRelationService.save(semiMatRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:semimatrelation:update")
    public R update(@RequestBody SemiMatRelationEntity semiMatRelation){
		semiMatRelationService.updateById(semiMatRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:semimatrelation:delete")
    public R delete(@RequestBody Long[] relIds){
		semiMatRelationService.removeByIds(Arrays.asList(relIds));

        return R.ok();
    }

}
