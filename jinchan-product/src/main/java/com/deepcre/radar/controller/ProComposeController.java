package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.deepcre.radar.vo.ProRelationReqVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.ProComposeEntity;
import com.deepcre.radar.service.ProComposeService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-28 11:23:13
 */
@RestController
@RequestMapping("radar/procompose")
public class ProComposeController {
    @Autowired
    private ProComposeService proComposeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:procompose:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = proComposeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{relId}")
    @RequiresPermissions("radar:procompose:info")
    public R info(@PathVariable("relId") Long relId){
		ProComposeEntity proCompose = proComposeService.getById(relId);

        return R.ok().put("proCompose", proCompose);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("radar:procompose:save")
    public R save(@RequestBody ProComposeEntity proCompose){
		proComposeService.save(proCompose);

        return R.ok();
    }


    /**
     * 产品组成以树形结构返回
     */
    @RequestMapping("/list/tree")
    public R listWithTree(@RequestParam Map<String, Object> params){
        List<ProComposeEntity> proComposeEntities = proComposeService.listWithTree(params);

        return R.ok().put("list",proComposeEntities);
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("radar:procompose:update")
    public R update(@RequestBody ProComposeEntity proCompose){
		proComposeService.updateById(proCompose);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("radar:procompose:delete")
    public R delete(@RequestBody Long[] relIds){
		proComposeService.removeByIds(Arrays.asList(relIds));

        return R.ok();
    }

}
