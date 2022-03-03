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

import com.deepcre.radar.entity.IdentityEntity;
import com.deepcre.radar.service.IdentityService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 标识表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/identity")
public class IdentityController {
    @Autowired
    private IdentityService identityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:identity:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = identityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{identityId}")
    @RequiresPermissions("radar:identity:info")
    public R info(@PathVariable("identityId") String identityId){
		IdentityEntity identity = identityService.getById(identityId);

        return R.ok().put("identity", identity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:identity:save")
    public R save(@RequestBody IdentityEntity identity){
		identityService.save(identity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:identity:update")
    public R update(@RequestBody IdentityEntity identity){
		identityService.updateById(identity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:identity:delete")
    public R delete(@RequestBody String[] identityIds){
		identityService.removeByIds(Arrays.asList(identityIds));

        return R.ok();
    }

}
