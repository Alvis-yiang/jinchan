package com.deepcre.radar.dao;

import com.deepcre.radar.entity.SemimanufacturesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 半成品信息表：一个id代表一种半成品，由名称和型号共同决定。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface SemimanufacturesDao extends BaseMapper<SemimanufacturesEntity> {
	
}
