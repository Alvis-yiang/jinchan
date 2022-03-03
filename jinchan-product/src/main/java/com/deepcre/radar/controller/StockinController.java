package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.deepcre.radar.vo.StockinReqVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.StockinEntity;
import com.deepcre.radar.service.StockinService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;

import javax.sound.midi.Soundbank;


/**
 * 入库表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/stockin")
public class StockinController {
    @Autowired
    private StockinService stockinService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:stockin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockinService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 确认入库品是否存在
     */
    @RequestMapping("/isexit")
    public R isExit(@RequestParam Map<String, Object> params){
        Map result = stockinService.isExit(params);
        return R.ok().put("data",result.get("isexit")).put("id",result.get("id"));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{inId}")
    @RequiresPermissions("radar:stockin:info")
    public R info(@PathVariable("inId") Long inId){
		StockinEntity stockin = stockinService.getById(inId);

        return R.ok().put("stockin", stockin);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:stockin:save")
    public R save(@RequestBody StockinReqVo stockinReqVo){
//		stockinService.save(stockinReqVo);
//        System.out.println("接收到的数据："+stockinReqVo);
//        System.out.println(stockinReqVo.getPrice());
//        System.out.println("时间："+stockinReqVo.getInDate());
        List productNos = stockinService.stockin(stockinReqVo);
//        System.out.println(productNos);

        return R.ok().put("productNos",productNos);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:stockin:update")
    public R update(@RequestBody StockinEntity stockin){
		stockinService.updateById(stockin);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:stockin:delete")
    public R delete(@RequestBody Long[] inIds){
		stockinService.removeByIds(Arrays.asList(inIds));

        return R.ok();
    }

}
