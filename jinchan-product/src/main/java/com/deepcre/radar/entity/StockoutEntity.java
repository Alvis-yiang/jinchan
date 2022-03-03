package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 出库表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_stockout")
public class StockoutEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 出库id，主键自增
	 */
	@TableId
	private Long outId;
	/**
	 * 出库品类别id
	 */
	private Long catId;
	/**
	 * 出库品id
	 */
	private Long goodsId;
	/**
	 * 出库数量
	 */
	/**
	 * 提交出库请求时间
	 */
	private Date applyDate;
	/**
	 * 出库数量
	 */
	private Integer count;
	/**
	 * 出库日期
	 */
	private Date outDate;
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
	/**
	 * 状态
	 * 1:请求提交;2:出库单已生成;3:同意领取;4:出库完成
	 */
	private Integer state;


}
