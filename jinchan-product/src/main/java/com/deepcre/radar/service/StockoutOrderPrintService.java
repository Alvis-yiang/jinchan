package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.StockoutOrderPrintEntity;

import java.util.Map;

/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-22 13:29:09
 */
public interface StockoutOrderPrintService extends IService<StockoutOrderPrintEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

