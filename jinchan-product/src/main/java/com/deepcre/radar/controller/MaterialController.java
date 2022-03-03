package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.Map;

import com.deepcre.radar.vo.MatRespVo;
import com.deepcre.radar.vo.SemiRespVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deepcre.radar.entity.MaterialEntity;
import com.deepcre.radar.service.MaterialService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 原料信息表：一个id代表一种原料，由名称和型号共同决定。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:material:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = materialService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{matId}")
    @RequiresPermissions("radar:material:info")
    public R info(@PathVariable("matId") Long matId){
		MaterialEntity material = materialService.getById(matId);

        return R.ok().put("material", material);
    }

    /* 信息
     */
    @RequestMapping("/matinfo/{matId}")
    public R matinfo(@PathVariable("matId") Long matId){
        MatRespVo matRespVo = materialService.getDetailInfoById(matId);
        return R.ok().put("mat", matRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:material:save")
    public R save(@RequestBody MaterialEntity material){
		materialService.save(material);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:material:update")
    public R update(@RequestBody MaterialEntity material){
		materialService.updateById(material);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:material:delete")
    public R delete(@RequestBody Long[] matIds){
		materialService.removeByIds(Arrays.asList(matIds));
        return R.ok();
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/img/upload",method = RequestMethod.POST)
    public R uploadImg(@RequestParam("matImg") MultipartFile multipartFile, @RequestParam("matId") Long matId){
        if (!multipartFile.isEmpty()){
            try{
                materialService.uploadImg(multipartFile,matId);
                return R.ok();
            }catch (Exception e){
                e.printStackTrace();
                return R.error("服务器出错");
            }
        }
        return R.error("未接收到文件");
    }

}
