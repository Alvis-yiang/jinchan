package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 生产计划表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_produce_plan")
public class ProducePlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 计划id，主键自增
	 */
	@TableId
	private Long planId;
	/**
	 * 产品id
	 */
	private Long productId;
	/**
	 * 生产数量
	 */
	private Double count;
	/**
	 * 负责员工id
	 */
	private Long empid;
	/**
	 * 计划开始日期
	 */
	private Date startDate;
	/**
	 * 计划完成日期
	 */
	private Date deadline;
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
