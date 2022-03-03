package com.deepcre.radar.vo;

import com.deepcre.radar.entity.MaterialEntity;
import com.deepcre.radar.entity.ProcurementEntity;
import lombok.Data;

/**
 * @author alvis-yiang
 * @create 2022-02-10 9:18 AM
 */
@Data
public class ProcurementInfoRespVo extends ProcurementEntity {
    /**
     * 原料名称
     */
    private String matName;

    /**
     * 型号
     */
    private String matModel;

    /**
     * 描述
     */
    private String matDescription;

    /**
     * 单位
     */
    private String matUnit;
}
