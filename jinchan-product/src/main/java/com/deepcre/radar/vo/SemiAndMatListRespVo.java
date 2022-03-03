package com.deepcre.radar.vo;

import lombok.Data;

import java.util.List;

/**
 * @author alvis-yiang
 * @create 2022-01-24 4:27 PM
 */
@Data
public class SemiAndMatListRespVo {
    /**
     * id
     */
    private Long id;

    /**
     * 名称+型号
     */
    private String nameAndModel;

    /**
     * 子元素
     */
    private List<MatForSemiRespVo> children;
}
