package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-17 14:29:13
 */
@Data
@TableName("tbl_tag")
public class TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long tagId;
	/**
	 * 
	 */
	private String tagName;
	/**
	 * 
	 */
	private Integer tagLevel;
	/**
	 * 
	 */
	private Long tagParentId;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	@TableLogic
	private Integer logicFlag;

}
