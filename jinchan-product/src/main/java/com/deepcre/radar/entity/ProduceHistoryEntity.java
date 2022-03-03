package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 生产记录表
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_produce_history")
public class ProduceHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录id，主键自增
	 */
	@TableId
	private Long hisId;
	/**
	 * 计划id
	 */
	private Long planId;
	/**
	 * 产品标识
	 */
	private String identityId;
	/**
	 * 实际完成时间
	 */
	private Date finishDate;
	/**
	 * 实际负责员工id
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
