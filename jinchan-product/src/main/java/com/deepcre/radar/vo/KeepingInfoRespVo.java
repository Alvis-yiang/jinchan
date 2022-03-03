package com.deepcre.radar.vo;

import com.deepcre.radar.entity.KeepingEntity;
import lombok.Data;

import java.util.List;

/**
 * @author alvis-yiang
 * @create 2022-01-12 10:51 AM
 * 关于库存品存放位置等具体信息
 */
@Data
public class KeepingInfoRespVo extends KeepingEntity {
    /**
     * 库存品名称
     */
    String name;

    /**
     * 库存品型号
     */
    String model;

    /**
     * 库存品描述信息
     */
    private String description;

    /**
     * 库存品单位
     */
    String unit;

    /**
     * 产品编号
     */
    String productNo;

    /**
     * 仓库名称
     */
    String storName;
}
