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
 * 仓库表：一级：A-Z区；二级：1-？号；三级：1-？层
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_storage")
public class StorageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 仓库id，主键自增
	 */
	@TableId
	private Long storId;
	/**
	 * 仓库名称
	 */
	private String storName;
	/**
	 * 父级id
	 */
	private Long parentId;
	/**
	 * 层级：0为一级仓库，1为二级仓库，以此类推
	 */
	private Long level;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 记录是否删除；未删除：1，删除：0
	 */
	@TableLogic
	private Integer logicFlag;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private List<StorageEntity> children;

}
