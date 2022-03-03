package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 原料信息表：一个id代表一种原料，由名称和型号共同决定。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_material")
public class MaterialEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 原料id，主键自增
	 */
	@TableId
	private Long matId;
	/**
	 * 原料名称
	 */
	private String matName;
	/**
	 * 型号
	 */
	private String matModel;
	/**
	 * 原料描述
	 */
	private String matDescription;
	/**
	 * 单位
	 */
	private String matUnit;
	/**
	 * 类别id
	 */
	private Long catId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 一个id代表一种产品，由名称和型号共同决定。
	 */
	@TableLogic
	private Integer logicFlag;
	/**
	 * 图片地址
	 */
	private String imgUrl;

}
