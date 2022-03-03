package com.deepcre.radar.vo;

import com.deepcre.radar.entity.SemimanufacturesEntity;
import lombok.Data;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 10:21 2022/3/2
 * @Description:
 */
@Data
public class SemiRespVo extends SemimanufacturesEntity {
    /**
     * 类别名称
     */
    private String catName;
}
