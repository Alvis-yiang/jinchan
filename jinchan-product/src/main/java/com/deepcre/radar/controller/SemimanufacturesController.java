package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.deepcre.radar.vo.ProductRespVo;
import com.deepcre.radar.vo.SemiRespVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deepcre.radar.entity.SemimanufacturesEntity;
import com.deepcre.radar.service.SemimanufacturesService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 半成品信息表：一个id代表一种半成品，由名称和型号共同决定。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/semimanufactures")
public class SemimanufacturesController {
    @Autowired
    private SemimanufacturesService semimanufacturesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:semimanufactures:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = semimanufacturesService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 将原料作为子节点封装返回
     * @param params
     * @return
     */
    @RequestMapping("/listbytree")
    public R listByTree(@RequestParam Map<String, Object> params){
        List list = semimanufacturesService.queryByTree(params);
        return R.ok().put("list",list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{semiId}")
    @RequiresPermissions("radar:semimanufactures:info")
    public R info(@PathVariable("semiId") Long semiId){
		SemimanufacturesEntity semimanufactures = semimanufacturesService.getById(semiId);

        return R.ok().put("semimanufactures", semimanufactures);
    }

    /* 信息
     */
    @RequestMapping("/semiInfo/{semiId}")
    public R semiinfo(@PathVariable("semiId") Long semiId){
        SemiRespVo semiRespVo = semimanufacturesService.getDetailInfoById(semiId);
        return R.ok().put("semi", semiRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:semimanufactures:save")
    public R save(@RequestBody SemimanufacturesEntity semimanufactures){
		semimanufacturesService.save(semimanufactures);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:semimanufactures:update")
    public R update(@RequestBody SemimanufacturesEntity semimanufactures){
		semimanufacturesService.updateById(semimanufactures);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:semimanufactures:delete")
    public R delete(@RequestBody Long[] semiIds){
		semimanufacturesService.removeByIds(Arrays.asList(semiIds));

        return R.ok();
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/img/upload",method = RequestMethod.POST)
    public R uploadImg(@RequestParam("semiImg") MultipartFile multipartFile, @RequestParam("semiId") Long semiId){
        if (!multipartFile.isEmpty()){
            try{
                semimanufacturesService.uploadImg(multipartFile,semiId);
                return R.ok();
            }catch (Exception e){
                e.printStackTrace();
                return R.error("服务器出错");
            }
        }
        return R.error("未接收到文件");
    }

}
