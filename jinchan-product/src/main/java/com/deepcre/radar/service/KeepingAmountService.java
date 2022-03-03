package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.KeepingAmountEntity;

import java.util.Map;

/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface KeepingAmountService extends IService<KeepingAmountEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryProductPage(Map<String,Object> params);

    PageUtils querySemiPage(Map<String, Object> params);

    PageUtils queryMaterialPage(Map<String,Object> params);

}

