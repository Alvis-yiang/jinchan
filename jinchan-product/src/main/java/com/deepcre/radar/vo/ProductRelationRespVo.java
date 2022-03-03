package com.deepcre.radar.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author alvis-yiang
 * @create 2022-01-23 10:40 AM
 */
@Data
public class ProductRelationRespVo {
    /**
     * 产品id
     */
    private Long product_id;

    /**
     * 产品名称
     */
    private String product_name;

    /**
     * 产品型号
     */
    private String product_model;

    /**
     * 单位
     */
    private String product_unit;

    /**
     * 产品系列id
     */
    private Long series_id;

    /**
     * 系列名称
     */
    private String series_name;

    /**
     * 产品描述
     */
    private String product_description;

    /**
     * 半成品列表
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List children;
}
