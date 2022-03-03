package com.deepcre.radar.vo;

import com.deepcre.radar.entity.StockoutOrderEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 10:15 2022/2/22
 * @Description: 同意出库，返回给前端的出库单内信息，包含出库品以及对应到相应仓库领料信息列表
 */
@Data
public class AgreeStockoutDetailInfoRespVo {
    /**
     *出库单号
     */
    private String orderId;
    /**
     * 申请人姓名
     */
    private String applyEmpName;
    /**
     *申请时间
     */
    private Date createDate;
    /**
     * 审核人姓名
     */
    private String auditEmpName;
    /**
     *审核时间
     */
    private Date auditDate;
    /**
     *审核结果
     */
    private Integer auditResult;
    /**
     * PDF地址
     */
    private String filePath;

    private List<AgreeStockoutGoodsAndStorInfoRespVo> list;

//    /**
//     * 出库品类型名称
//     */
//    private String goodsCatName;
//    /**
//     * 出库品名称
//     */
//    private String goodsName;
//    /**
//     * 出库品型号
//     */
//    private String goodsModel;
//    /**
//     * 仓库名称
//     */
//    private String storName;
//    /**
//     * 数量
//     */
//    private Integer amount;
}
