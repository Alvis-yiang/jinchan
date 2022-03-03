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
 * @date 2022-02-17 14:29:13
 */
@Data
@TableName("tbl_stockout_order")
public class StockoutOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String orderId;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Long createEmpid;
	/**
	 * 
	 */
	private Long auditEmpid;
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
	private String remark;
	/**
	 * 
	 */
	@TableLogic
	private Integer logicFlag;
	/**
	 * pdf保存路径
	 */
	private String filePath;

}
