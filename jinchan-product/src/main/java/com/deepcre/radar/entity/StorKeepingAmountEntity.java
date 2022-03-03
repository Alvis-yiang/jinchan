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
 * @date 2022-02-21 16:10:21
 */
@Data
@TableName("tbl_stor_keeping_amount")
public class StorKeepingAmountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long storId;
	/**
	 * 
	 */
	private Long goodsCatid;
	/**
	 * 
	 */
	private Long goodsId;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	@TableLogic
	private Integer logicFlag;

}
