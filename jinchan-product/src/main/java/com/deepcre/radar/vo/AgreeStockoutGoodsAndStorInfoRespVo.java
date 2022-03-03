package com.deepcre.radar.vo;

import lombok.Data;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 10:47 2022/2/22
 * @Description:出库品以及对应到相应仓库领料信息列表
 */
@Data
public class AgreeStockoutGoodsAndStorInfoRespVo {
    /**
     * 出库品类型名称
     */
    private String goodsCatName;
    /**
     * 出库品名称
     */
    private String goodsName;
    /**
     * 出库品型号
     */
    private String goodsModel;
    /**
     * 单位
     */
    private String unit;
    /**
     * 仓库名称
     */
    private String storName;
    /**
     * 数量
     */
    private Integer amount;
}

