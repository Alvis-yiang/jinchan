package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 销售表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_sale")
public class SaleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 销售id，主键自增
	 */
	@TableId
	private Long saleId;
	/**
	 * 产品id
	 */
	private Long productId;
	/**
	 * 产品标识id
	 */
	private String identityId;
	/**
	 * 客户id
	 */
	private Long clientId;
	/**
	 * 下单时间
	 */
	private Date orderDate;
	/**
	 * 出库时间
	 */
	private Date outDate;
	/**
	 * 销售员工id
	 */
	private Long empid;
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
