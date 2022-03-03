package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.Map;

import com.deepcre.radar.vo.ProRelationReqVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.ProSemiRelationEntity;
import com.deepcre.radar.service.ProSemiRelationService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 产品半成品关系表：一种产品对应多种半成品
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/prosemirelation")
public class ProSemiRelationController {
    @Autowired
    private ProSemiRelationService proSemiRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:prosemirelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = proSemiRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表,组装成树形结构
     */
    @RequestMapping("/listbytree")
//    @RequiresPermissions("radar:prosemirelation:list")
    public R listByTree(@RequestParam Map<String, Object> params){
        PageUtils page = proSemiRelationService.queryPageByTree(params);

        return R.ok().put("page", page);
    }





    /**
     * 信息
     */
    @RequestMapping("/info/{relId}")
    @RequiresPermissions("radar:prosemirelation:info")
    public R info(@PathVariable("relId") Long relId){
		ProSemiRelationEntity proSemiRelation = proSemiRelationService.getById(relId);

        return R.ok().put("proSemiRelation", proSemiRelation);
    }


    /**
     * 接收新增产品组成
     */
    @RequestMapping("/saveRelation")
    public R saveRelation(@RequestBody ProRelationReqVo proRelationReqVo){
        proSemiRelationService.saveRelation(proRelationReqVo);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:prosemirelation:save")
    public R save(@RequestBody ProSemiRelationEntity proSemiRelation){
		proSemiRelationService.save(proSemiRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:prosemirelation:update")
    public R update(@RequestBody ProSemiRelationEntity proSemiRelation){
		proSemiRelationService.updateById(proSemiRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:prosemirelation:delete")
    public R delete(@RequestBody Long[] relIds){
		proSemiRelationService.removeByIds(Arrays.asList(relIds));

        return R.ok();
    }

}
