package com.deepcre.radar.vo;

import com.deepcre.radar.entity.KeepingAmountEntity;
import lombok.Data;

import java.util.List;

/**
 * @author alvis-yiang
 * @create 2022-01-11 3:23 PM
 */
@Data
public class KeepingRespVo extends KeepingAmountEntity {
    /**
     * 库存品名称
     */
    private String name;

    /**
     * 库存品型号
     */
    private String model;

    /**
     * 库存品描述信息
     */
    private String description;

    /**
     * 单位
     */
    private String unit;

}
