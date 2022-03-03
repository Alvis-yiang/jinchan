package com.deepcre.radar.dao;

import com.deepcre.radar.entity.ProSemiDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品半成品生产记录表：记录每一件产品对应的指定的半成品
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface ProSemiDetailDao extends BaseMapper<ProSemiDetailEntity> {
	
}
