package com.deepcre.radar.dao;

import com.deepcre.radar.entity.ProductSalEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品销售属性表：一个id代表一个产品，根据产品id确定该产品属于哪种型号的哪种产品。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface ProductSalDao extends BaseMapper<ProductSalEntity> {
	
}
