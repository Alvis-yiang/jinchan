package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.SeriesEntity;

import java.util.List;
import java.util.Map;

/**
 * 系列表：喷金划膜系列、切条切料系列、压壳系列、读数系列等。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface SeriesService extends IService<SeriesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List queryPageByTree(Map<String,Object> params);
}

