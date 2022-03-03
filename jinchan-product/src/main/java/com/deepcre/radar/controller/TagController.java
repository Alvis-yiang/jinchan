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

import com.deepcre.radar.entity.TagEntity;
import com.deepcre.radar.generator.service.TagService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-17 14:29:13
 */
@RestController
@RequestMapping("generator/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:tag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tagId}")
    @RequiresPermissions("generator:tag:info")
    public R info(@PathVariable("tagId") Long tagId){
		TagEntity tag = tagService.getById(tagId);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:tag:save")
    public R save(@RequestBody TagEntity tag){
		tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:tag:update")
    public R update(@RequestBody TagEntity tag){
		tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:tag:delete")
    public R delete(@RequestBody Long[] tagIds){
		tagService.removeByIds(Arrays.asList(tagIds));

        return R.ok();
    }

}
