package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.CategoryEntity;

import java.util.Map;

/**
 * 库存品类别表：成品，半成品和原料三种类别
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

