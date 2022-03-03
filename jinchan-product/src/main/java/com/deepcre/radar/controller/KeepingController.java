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

import com.deepcre.radar.entity.KeepingEntity;
import com.deepcre.radar.service.KeepingService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 库存表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/keeping")
public class KeepingController {
    @Autowired
    private KeepingService keepingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:keeping:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = keepingService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询库存品存放位置等具体信息
     */
    @RequestMapping("/keepingInfo/{catId}/{goodsId}")
    public R keepingInfo(@RequestParam Map<String, Object> params,@PathVariable("catId") Long catId,@PathVariable("goodsId") Long goodsId){
        PageUtils page = keepingService.queryKeepingInfo(params,catId,goodsId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{keepId}")
    @RequiresPermissions("radar:keeping:info")
    public R info(@PathVariable("keepId") Long keepId){
		KeepingEntity keeping = keepingService.getById(keepId);

        return R.ok().put("keeping", keeping);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:keeping:save")
    public R save(@RequestBody KeepingEntity keeping){
		keepingService.save(keeping);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:keeping:update")
    public R update(@RequestBody KeepingEntity keeping){
		keepingService.updateById(keeping);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:keeping:delete")
    public R delete(@RequestBody Long[] keepIds){
		keepingService.removeByIds(Arrays.asList(keepIds));

        return R.ok();
    }

    /**
     * 出库
     */
    @RequestMapping("/stockout")
//    @RequiresPermissions("radar:keeping:delete")
    public R stockout(@RequestParam Map<String, Object> params){
        int result = keepingService.stockout(params);//0:产品没有销售记录; 1:出库成功;
        if (result==1){
            return R.ok();

        }
        if (result==0) {
            return R.error(100, "产品没有销售记录");
        }
        return R.error();
    }

}
