package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 库存表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_keeping")
public class KeepingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 库存id，主键自增
	 */
	@TableId
	private Long keepId;
	/**
	 * 库存品类别id
	 */
	private Long catId;
	/**
	 * 库存品id
	 */
	private Long goodsId;
	/**
	 * 库存品标识id
	 */
	private String identityId;
	/**
	 * 仓库id
	 */
	private Long storId;
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
	 * 是否在仓库：1：在；0：已出库; 2：同意出库
	 */
	private Integer isAvailable;

}
