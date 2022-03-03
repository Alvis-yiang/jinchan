package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.SemiMatDetailEntity;

import java.util.Map;

/**
 * 半成品原料生产记录表：记录每一件半成品对应的指定的原料
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface SemiMatDetailService extends IService<SemiMatDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

