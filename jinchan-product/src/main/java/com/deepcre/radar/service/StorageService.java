package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.StorageEntity;

import java.util.List;
import java.util.Map;

/**
 * 仓库表：一级：A-Z区；二级：1-？号；三级：1-？层
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface StorageService extends IService<StorageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查出所有库位以及子库位，以树形结构组装起来
     */
    List<StorageEntity> listWithTree();

    /**
     * 根据id删除库位
     */
    void removeStorByIds(List<Long> asList);
}

