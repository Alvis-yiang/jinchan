package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 入库表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_stockin")
public class StockinEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 入库id
	 */
	@TableId
	private Long inId;
	/**
	 * 入库品类别id
	 */
	private Long catId;
	/**
	 * 入库品id
	 */
	private Long goodsId;
	/**
	 * 仓库id
	 */
	private Long storId;
	/**
	 * 入库数量
	 */
	private Double count;
	/**
	 * 入库时间
	 */
	private Date inDate;
	/**
	 * 操作员工id
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
