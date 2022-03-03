package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.IdentityEntity;

import java.util.Map;

/**
 * 标识表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface IdentityService extends IService<IdentityEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

