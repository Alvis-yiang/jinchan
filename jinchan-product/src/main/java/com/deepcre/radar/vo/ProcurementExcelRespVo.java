package com.deepcre.radar.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author alvis-yiang
 * @create 2022-02-10 2:25 PM
 */
@Data
public class ProcurementExcelRespVo {
    /**
     * 原料名称
     */
    @Excel(name ="原料名称",orderNum = "0")
    private String matName;

    /**
     * 型号
     */
    @Excel(name ="型号",orderNum = "1")
    private String matModel;

    /**
     * 需求量
     */
    @Excel(name = "需求量", orderNum = "2")
    private Integer num;

    /**
     * 描述
     */
    @Excel(name = "描述", orderNum = "4")
    private String matDescription;

    /**
     * 单位
     */
    @Excel(name = "单位", orderNum = "3")
    private String matUnit;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "5")
    private String remark;
}
