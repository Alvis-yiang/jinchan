package com.deepcre.radar.dao;

import com.deepcre.radar.entity.SeriesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系列表：喷金划膜系列、切条切料系列、压壳系列、读数系列等。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface SeriesDao extends BaseMapper<SeriesEntity> {
	
}
