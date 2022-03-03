package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.KeepingEntity;

import java.util.Map;

/**
 * 库存表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface KeepingService extends IService<KeepingEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryKeepingInfo(Map<String,Object> params, Long catId, Long goodsId);

    int stockout(Map<String,Object> params);
}

