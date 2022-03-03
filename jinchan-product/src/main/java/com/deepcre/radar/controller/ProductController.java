package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.Map;

import com.deepcre.radar.vo.ProductRespVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deepcre.radar.entity.ProductEntity;
import com.deepcre.radar.service.ProductService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.RequestWrapper;


/**
 * 产品信息表：一个id代表一种产品，由名称和型号共同决定。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@RestController
@RequestMapping("radar/product")
public class ProductController {
    @Autowired
    private ProductService productService;



    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:product:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{productId}")
    @RequiresPermissions("radar:product:info")
    public R info(@PathVariable("productId") Long productId){
		ProductEntity product = productService.getById(productId);
        return R.ok().put("product", product);
    }

    /* 信息
     */
    @RequestMapping("/productinfo/{productId}")
    public R productinfo(@PathVariable("productId") Long productId){
        ProductRespVo productInfo = productService.getDetailInfoById(productId);
        return R.ok().put("product", productInfo);
    }



    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:product:save")
    public R save(@RequestBody ProductEntity product){
		productService.save(product);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:product:update")
    public R update(@RequestBody ProductEntity product){
		productService.updateById(product);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:product:delete")
    public R delete(@RequestBody Long[] productIds){
		productService.removeByIds(Arrays.asList(productIds));

        return R.ok();
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/img/upload",method = RequestMethod.POST)
    public R uploadImg(@RequestParam("productImg") MultipartFile multipartFile,@RequestParam("productId") Long productId){
        if (!multipartFile.isEmpty()){
            try{
                productService.uploadImg(multipartFile,productId);
                return R.ok();
            }catch (Exception e){
                e.printStackTrace();
                return R.error("服务器出错");
            }
        }
        return R.error("未接收到文件");
    }

}
