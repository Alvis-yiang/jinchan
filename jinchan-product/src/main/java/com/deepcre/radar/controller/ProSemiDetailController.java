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

import com.deepcre.radar.entity.ProSemiDetailEntity;
import com.deepcre.radar.service.ProSemiDetailService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 产品半成品生产记录表：记录每一件产品对应的指定的半成品
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/prosemidetail")
public class ProSemiDetailController {
    @Autowired
    private ProSemiDetailService proSemiDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:prosemidetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = proSemiDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{detId}")
    @RequiresPermissions("radar:prosemidetail:info")
    public R info(@PathVariable("detId") Long detId){
		ProSemiDetailEntity proSemiDetail = proSemiDetailService.getById(detId);

        return R.ok().put("proSemiDetail", proSemiDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:prosemidetail:save")
    public R save(@RequestBody ProSemiDetailEntity proSemiDetail){
		proSemiDetailService.save(proSemiDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:prosemidetail:update")
    public R update(@RequestBody ProSemiDetailEntity proSemiDetail){
		proSemiDetailService.updateById(proSemiDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:prosemidetail:delete")
    public R delete(@RequestBody Long[] detIds){
		proSemiDetailService.removeByIds(Arrays.asList(detIds));

        return R.ok();
    }

}
