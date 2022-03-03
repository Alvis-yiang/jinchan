package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品销售属性表：一个id代表一个产品，根据产品id确定该产品属于哪种型号的哪种产品。
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
@Data
@TableName("tbl_product_sal")
public class ProductSalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id，主键自增
	 */
	@TableId
	private Long proSalId;
	/**
	 * 产品id
	 */
	private Long productId;
	/**
	 * 产品标识id
	 */
	private String identityId;
	/**
	 * 产品编号,每个产品唯一标识
	 */
	private String productNo;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 销售状态，1:未出售，0:已出售
	 */
	private Integer salFlag;
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
