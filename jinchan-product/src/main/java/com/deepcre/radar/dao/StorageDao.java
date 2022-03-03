package com.deepcre.radar.dao;

import com.deepcre.radar.entity.StorageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库表：一级：A-Z区；二级：1-？号；三级：1-？层
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Mapper
public interface StorageDao extends BaseMapper<StorageEntity> {
	
}
