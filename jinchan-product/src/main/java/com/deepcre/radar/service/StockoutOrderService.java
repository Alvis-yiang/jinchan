package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.StockoutOrderEntity;

import java.util.Map;

/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-17 14:29:13
 */
public interface StockoutOrderService extends IService<StockoutOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String createOrder(Long[] stockoutIds, Long empId);

    void audit(StockoutOrderEntity stockoutOrder);

    PageUtils printStockoutOrder(Map<String, Object> params);
}

