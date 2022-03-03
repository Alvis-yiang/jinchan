package com.deepcre.radar.dao;

import com.deepcre.radar.entity.SemiMatDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 半成品原料生产记录表：记录每一件半成品对应的指定的原料
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface SemiMatDetailDao extends BaseMapper<SemiMatDetailEntity> {
	
}
