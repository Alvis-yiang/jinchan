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

import com.deepcre.radar.entity.StorageEntity;
import com.deepcre.radar.service.StorageService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 仓库表：一级：A-Z区；二级：1-？号；三级：1-？层
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    /**
     * 查出所有库位以及子库位，以树形结构组装起来
     */
    @RequestMapping("/list/tree")
    @RequiresPermissions("radar:storage:list")
    public R list(@RequestParam Map<String, Object> params){
        List<StorageEntity> entities = storageService.listWithTree();

        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{storId}")
    @RequiresPermissions("radar:storage:info")
    public R info(@PathVariable("storId") Long storId){
		StorageEntity storage = storageService.getById(storId);

        return R.ok().put("data", storage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:storage:save")
    public R save(@RequestBody StorageEntity storage){
		storageService.save(storage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:storage:update")
    public R update(@RequestBody StorageEntity storage){
		storageService.updateById(storage);

        return R.ok();
    }

    /**
     * 删除
     * @RequestBody 获取请求体，必须为post请求
     * SpringMVC自动将请求体的数据（json），转为对应的对象
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("radar:storage:delete")
    public R delete(@RequestBody Long[] storIds){

        //1.检查当前要删除的库位，是否被其他地方引用
		//storageService.removeByIds(Arrays.asList(storIds));

		storageService.removeStorByIds(Arrays.asList(storIds));

        return R.ok();
    }

}
