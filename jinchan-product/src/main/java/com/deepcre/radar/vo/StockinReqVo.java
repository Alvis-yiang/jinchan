package com.deepcre.radar.vo;

import com.deepcre.radar.entity.StockinEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author alvis-yiang
 * @create 2022-01-13 2:59 PM
 */
@Data
public class StockinReqVo extends StockinEntity {
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 型号
     */
    private String model;
}
