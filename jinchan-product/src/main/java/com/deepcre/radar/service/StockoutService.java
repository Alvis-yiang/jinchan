package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.StockoutEntity;
import com.deepcre.radar.vo.ApplyStockoutRespVo;

import java.util.List;
import java.util.Map;

/**
 * 出库表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface StockoutService extends IService<StockoutEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean applyStockout(StockoutEntity stockout);

    List<ApplyStockoutRespVo> queryPageByUser(Map<String, Object> params);
}

