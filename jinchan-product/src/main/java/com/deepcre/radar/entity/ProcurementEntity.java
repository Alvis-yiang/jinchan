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
 * @date 2022-02-09 14:26:10
 */
@Data
@TableName("tbl_procurement")
public class ProcurementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long procurementId;
	/**
	 * 
	 */
	private Long goodsId;
	/**
	 * 
	 */
	private Integer num;
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
