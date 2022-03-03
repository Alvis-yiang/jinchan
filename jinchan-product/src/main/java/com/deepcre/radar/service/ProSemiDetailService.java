package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProSemiDetailEntity;

import java.util.Map;

/**
 * 产品半成品生产记录表：记录每一件产品对应的指定的半成品
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface ProSemiDetailService extends IService<ProSemiDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

