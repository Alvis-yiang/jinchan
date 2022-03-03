package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 标识表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_identity")
public class IdentityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标识id，uuid生成
	 */
	@TableId
	private String identityId;
	/**
	 * 产品编号，每个产品唯一标识
	 */
	private String productNo;
	/**
	 * 类别id
	 */
	private Long catId;
	/**
	 * 库存品id
	 */
	private Long goodsId;
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
