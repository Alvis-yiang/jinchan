package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.deepcre.radar.vo.ApplyStockoutRespVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.StockoutEntity;
import com.deepcre.radar.service.StockoutService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 出库表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/stockout")
public class StockoutController {
    @Autowired
    private StockoutService stockoutService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:stockout:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockoutService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表:根据请求的用户id，查询该用户的出库申请
     */
    @RequestMapping("/list/user")
    public R listByUser(@RequestParam Map<String, Object> params){
        List<ApplyStockoutRespVo> list = stockoutService.queryPageByUser(params);

        return R.ok().put("list", list);
    }

    /**
     * 提交出库请求
     */
    @RequestMapping("/apply")
    public R apply(@RequestBody StockoutEntity stockout){
//        PageUtils page = stockoutService.queryPage(params);
        Boolean result = stockoutService.applyStockout(stockout);
        if (result){
            return R.ok();
        }else{
            return R.error().put("err","库存不足");
        }
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{outId}")
    @RequiresPermissions("radar:stockout:info")
    public R info(@PathVariable("outId") Long outId){
		StockoutEntity stockout = stockoutService.getById(outId);

        return R.ok().put("stockout", stockout);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:stockout:save")
    public R save(@RequestBody StockoutEntity stockout){
		stockoutService.save(stockout);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:stockout:update")
    public R update(@RequestBody StockoutEntity stockout){
		stockoutService.updateById(stockout);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:stockout:delete")
    public R delete(@RequestBody Long[] outIds){
		stockoutService.removeByIds(Arrays.asList(outIds));

        return R.ok();
    }

}
