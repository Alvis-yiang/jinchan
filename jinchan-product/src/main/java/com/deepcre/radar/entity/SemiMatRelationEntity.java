package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 半成品原料关系表：一种半成品对应多种原料
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_semi_mat_relation")
public class SemiMatRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关系id，主键自增
	 */
	@TableId
	private Long relId;
	/**
	 * 半成品id
	 */
	private Long semiId;
	/**
	 * 原料id
	 */
	private Long matId;
	/**
	 * 原料数量
	 */
	private Integer matNum;

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
