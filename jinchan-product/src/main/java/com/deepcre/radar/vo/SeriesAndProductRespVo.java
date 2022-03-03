package com.deepcre.radar.vo;

import lombok.Data;

import java.util.List;

/**
 * @author alvis-yiang
 * @create 2022-01-23 3:53 PM
 */
@Data
public class SeriesAndProductRespVo {
    /**
     * 系列id
     */
    private Long seriesId;

    /**
     * 系列名称
     */
    private String seriesName;



//    /**
//     * 产品id
//     */
//    private Long productId;
//
//    /**
//     * 产品名称
//     */
//    private String productName;
//
//    /**
//     * 产品型号
//     */
//    private String productModel;

    /**
     * 包含：产品id，产品名称，产品型号
     */
    private List children;
}
