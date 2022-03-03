package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_keeping_amount")
public class KeepingAmountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 库存品类别id
	 */
	@TableId
	private Long catId;
	/**
	 * 库存品id
	 */
	private Long goodsId;
	/**
	 * 库存数量
	 */
	private Double amount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 记录是否删除；未删除：1，删除：0
	 */
	@TableLogic
	private Integer logicFlag;

}
