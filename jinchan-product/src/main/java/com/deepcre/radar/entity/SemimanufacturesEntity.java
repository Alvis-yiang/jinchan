package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 半成品信息表：一个id代表一种半成品，由名称和型号共同决定。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_semimanufactures")
public class SemimanufacturesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 半成品id
	 */
	@TableId
	private Long semiId;
	/**
	 * 半成品名称
	 */
	private String semiName;
	/**
	 * 型号
	 */
	private String semiModel;
	/**
	 * 产品描述
	 */
	private String semiDescription;
	/**
	 * 单位
	 */
	private String semiUnit;
	/**
	 * 类别id
	 */
	private Long catId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 记录是否删除；未删除：1，删除：0
	 */
	@TableLogic
	private Integer logicFlag;
	/**
	 * 图片地址
	 */
	private String imgUrl;

}
