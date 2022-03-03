package com.deepcre.radar.dao;

import com.deepcre.radar.entity.SemiMatRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 半成品原料关系表：一种半成品对应多种原料
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface SemiMatRelationDao extends BaseMapper<SemiMatRelationEntity> {
	
}
