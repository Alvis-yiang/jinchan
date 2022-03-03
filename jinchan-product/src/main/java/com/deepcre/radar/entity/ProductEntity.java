package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品信息表：一个id代表一种产品，由名称和型号共同决定。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 产品id，主键自增
	 */
	@TableId
	private Long productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品型号
	 */
	private String productModel;
	/**
	 * 系列id
	 */
	private Long seriesId;
	/**
	 * 产品描述
	 */
	private String productDescription;
	/**
	 * 单位
	 */
	private String productUnit;
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
	private Integer logicFlag;
	/**
	 * 是否在售
	 */
	@TableLogic
	private Integer isSale;
	/**
	 * 图片地址
	 */
	private String imgUrl;


}
