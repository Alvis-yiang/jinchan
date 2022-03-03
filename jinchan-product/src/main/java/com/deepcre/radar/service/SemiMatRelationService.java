package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.SemiMatRelationEntity;

import java.util.Map;

/**
 * 半成品原料关系表：一种半成品对应多种原料
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface SemiMatRelationService extends IService<SemiMatRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

