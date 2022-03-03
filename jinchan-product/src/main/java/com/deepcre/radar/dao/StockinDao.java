package com.deepcre.radar.dao;

import com.deepcre.radar.entity.StockinEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 入库表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface StockinDao extends BaseMapper<StockinEntity> {
	
}
