package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProducePlanEntity;

import java.util.Map;

/**
 * 生产计划表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface ProducePlanService extends IService<ProducePlanEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

