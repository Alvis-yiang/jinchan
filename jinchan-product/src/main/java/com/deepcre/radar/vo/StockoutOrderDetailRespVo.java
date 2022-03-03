package com.deepcre.radar.vo;

import com.deepcre.radar.entity.StockoutOrderDetailEntity;
import com.deepcre.radar.entity.StockoutOrderEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 9:32 2022/2/18
 * @Description:
 */
@Data
public class StockoutOrderDetailRespVo extends StockoutOrderEntity {
    /**
     * 出库记录列表
     */
    private List<ApplyStockoutRespVo> list ;

    /**
     * 申请人
     */
    private String applyPerson;
}
