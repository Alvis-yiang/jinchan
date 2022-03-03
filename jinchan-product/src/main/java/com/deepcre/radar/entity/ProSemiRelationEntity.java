package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品半成品关系表：一种产品对应多种半成品
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_pro_semi_relation")
public class ProSemiRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关系id，主键自增
	 */
	@TableId
	private Long relId;
	/**
	 * 产品id
	 */
	private Long productId;
	/**
	 * 半成品id
	 */
	private Long semiId;
	/**
	 * 半成品数量
	 */
	private Integer semiNum;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 一个id代表一种产品，由名称和型号共同决定。
	 */
	@TableLogic
	private Integer logicFlag;

}
