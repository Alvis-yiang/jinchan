package com.deepcre.radar.vo;

import lombok.Data;

import java.util.List;

/**
 * @author alvis-yiang
 * @create 2022-01-28 10:45 AM
 */
@Data
public class ProRelationReqVo {

    private Long seriesId;

    private String seriesName;

    private String productName;

    private String productModel;

    private List semiAndMats;


}
