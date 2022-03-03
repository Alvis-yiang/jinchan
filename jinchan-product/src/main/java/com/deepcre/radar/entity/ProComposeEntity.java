package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-28 11:23:13
 */
@Data
@TableName("tbl_pro_compose")
public class ProComposeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long relId;
	/**
	 * 
	 */
	private Long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private String name;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private String model;
	/**
	 * 
	 */
	private Integer level;
	/**
	 * 
	 */
	private Integer num;
	/**
	 * 
	 */
	private Long parentRelId;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	@TableLogic
	private Integer logicFlag;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private List<ProComposeEntity> children;


}
