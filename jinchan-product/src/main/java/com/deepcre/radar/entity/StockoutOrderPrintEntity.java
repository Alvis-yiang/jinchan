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
 * @date 2022-02-22 13:29:09
 */
@Data
@TableName("tbl_stockout_order_print")
public class StockoutOrderPrintEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String stockoutOrderId;
	/**
	 * 
	 */
	private Long applyempId;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 
	 */
	private Long auditempId;
	/**
	 * 
	 */
	private Date auditDate;
	/**
	 * 
	 */
	private Integer auditResult;
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
	private Long storId;
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
