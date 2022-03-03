package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 生产状态表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_produce_statue")
public class ProduceStatueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 状态id，主键自增
	 */
	@TableId
	private Long statueId;
	/**
	 * 生产计划id
	 */
	private Long planId;
	/**
	 * 产品标识id
	 */
	private String identityId;
	/**
	 * 生产状态，1:计划创建；2:生产中；3:生产完成；4:入库
	 */
	private Integer statue;
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
