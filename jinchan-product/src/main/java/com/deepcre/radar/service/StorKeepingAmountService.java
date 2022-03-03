package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.StorKeepingAmountEntity;

import java.util.Map;

/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-21 16:10:21
 */
public interface StorKeepingAmountService extends IService<StorKeepingAmountEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

