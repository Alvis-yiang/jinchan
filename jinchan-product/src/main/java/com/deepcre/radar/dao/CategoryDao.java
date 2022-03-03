package com.deepcre.radar.dao;

import com.deepcre.radar.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存品类别表：成品，半成品和原料三种类别
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
