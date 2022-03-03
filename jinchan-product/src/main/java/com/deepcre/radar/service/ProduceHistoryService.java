package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProduceHistoryEntity;

import java.util.Map;

/**
 * 生产记录表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface ProduceHistoryService extends IService<ProduceHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

