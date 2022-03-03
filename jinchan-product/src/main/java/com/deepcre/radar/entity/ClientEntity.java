package com.deepcre.radar.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-19 15:40:49
 */
@Data
@TableName("tbl_client")
public class ClientEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户id，主键自增
	 */
	@TableId
	private Long clientId;
	/**
	 * 客户姓名
	 */
	private String clientName;
	/**
	 * 客户手机号
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 公司
	 */
	private String company;
	/**
	 * 客户地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 记录是否删除；未删除：1，删除：0
	 */
	private Integer logicFlag;

}
