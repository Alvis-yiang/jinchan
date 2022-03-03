package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.SaleEntity;

import java.util.Map;

/**
 * 销售表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface SaleService extends IService<SaleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryListPage(Map<String,Object> params);

    void sale(SaleEntity saleEntity);
}

