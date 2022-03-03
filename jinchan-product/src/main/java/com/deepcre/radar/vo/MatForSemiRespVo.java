package com.deepcre.radar.vo;

import lombok.Data;

/**
 * @author alvis-yiang
 * @create 2022-01-24 4:47 PM
 */
@Data
public class MatForSemiRespVo {
    private Long id;
    /**
     * 名称+型号
     */
    private String nameAndModel;
    /**
     * 父id：semiid
     */
    private Long parentId;
}
