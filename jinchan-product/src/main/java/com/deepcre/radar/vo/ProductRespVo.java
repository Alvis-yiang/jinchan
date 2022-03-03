package com.deepcre.radar.vo;

import com.deepcre.radar.entity.ProductEntity;
import lombok.Data;
import org.omg.CORBA.PERSIST_STORE;

/**
 * @author alvis-yiang
 * @create 2022-01-11 10:47 AM
 */
@Data
public class ProductRespVo extends ProductEntity {

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 类别名称
     */
    private String catName;

}
