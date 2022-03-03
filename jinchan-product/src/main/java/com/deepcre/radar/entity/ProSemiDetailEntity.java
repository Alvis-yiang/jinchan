package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品半成品生产记录表：记录每一件产品对应的指定的半成品
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_pro_semi_detail")
public class ProSemiDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 生产记录id，主键自增
	 */
	@TableId
	private Long detId;
	/**
	 * 产品id
	 */
	private Long productId;
	/**
	 * 产品标识id
	 */
	private String proIdentityId;
	/**
	 * 半成品id
	 */
	private Long semiId;
	/**
	 * 半成品标识id
	 */
	private String semiIdentityId;
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
