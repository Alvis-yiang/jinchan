package com.deepcre.radar.vo;

import com.deepcre.radar.entity.StockoutEntity;
import lombok.Data;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 16:52 2022/2/16
 * @Description:
 */
@Data
public class ApplyStockoutRespVo extends StockoutEntity {
    /**
     * 出库品类型名称
     */
    private String catName;

    /**
     * 出库品名称
     */
    private String goodsName;
    /**
     * 单位
     */
    private String unit;
    /**
     * 型号
     */
    private String model;
    /**
     * 描述
     */
    private String description;

}
