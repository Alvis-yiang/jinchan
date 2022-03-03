package com.deepcre.radar.vo;

import com.deepcre.radar.entity.ProductSalEntity;
import lombok.Data;

/**
 * @author alvis-yiang
 * @create 2022-01-18 12:34 PM
 */
@Data
public class ProductSalRespVo extends ProductSalEntity {
    /**
     * 名称
     */
    String productName;

    /**
     * 型号
     */
    String productModel;

    /**
     * 单位
     */
    String productUnit;

    /**
     * 存放位置
     */
    String storName;


}
