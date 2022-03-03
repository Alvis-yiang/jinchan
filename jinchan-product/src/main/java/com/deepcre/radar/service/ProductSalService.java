package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProductSalEntity;

import java.util.Map;

/**
 * 产品销售属性表：一个id代表一个产品，根据产品id确定该产品属于哪种型号的哪种产品。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface ProductSalService extends IService<ProductSalEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageInfo(Map<String,Object> params);
}

