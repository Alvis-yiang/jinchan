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

import com.deepcre.radar.entity.SemiMatDetailEntity;
import com.deepcre.radar.service.SemiMatDetailService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 半成品原料生产记录表：记录每一件半成品对应的指定的原料
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/semimatdetail")
public class SemiMatDetailController {
    @Autowired
    private SemiMatDetailService semiMatDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:semimatdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = semiMatDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{detId}")
    @RequiresPermissions("radar:semimatdetail:info")
    public R info(@PathVariable("detId") Long detId){
		SemiMatDetailEntity semiMatDetail = semiMatDetailService.getById(detId);

        return R.ok().put("semiMatDetail", semiMatDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:semimatdetail:save")
    public R save(@RequestBody SemiMatDetailEntity semiMatDetail){
		semiMatDetailService.save(semiMatDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:semimatdetail:update")
    public R update(@RequestBody SemiMatDetailEntity semiMatDetail){
		semiMatDetailService.updateById(semiMatDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:semimatdetail:delete")
    public R delete(@RequestBody Long[] detIds){
		semiMatDetailService.removeByIds(Arrays.asList(detIds));

        return R.ok();
    }

}
