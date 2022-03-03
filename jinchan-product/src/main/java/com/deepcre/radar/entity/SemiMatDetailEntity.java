package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 半成品原料生产记录表：记录每一件半成品对应的指定的原料
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_semi_mat_detail")
public class SemiMatDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 生产记录id，主键自增
	 */
	@TableId
	private Long detId;
	/**
	 * 半成品id
	 */
	private Long semiId;
	/**
	 * 半成品标识
	 */
	private String semiIdentityId;
	/**
	 * 原料id
	 */
	private Long matId;
	/**
	 * 原料标识id
	 */
	private String matIdentityId;
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
