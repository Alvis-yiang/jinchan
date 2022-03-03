package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系列表：喷金划膜系列、切条切料系列、压壳系列、读数系列等。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_series")
public class SeriesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 系列id，主键自增
	 */
	@TableId
	private Long seriesId;
	/**
	 * 系列名称
	 */
	private String seriesName;
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
