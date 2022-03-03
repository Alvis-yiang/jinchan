package com.deepcre.radar.jinchanemployee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.jinchanemployee.entity.SysUserEntity;

import java.util.Map;

/**
 * 系统用户
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-18 17:04:27
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SysUserEntity getUserInfoById(Long empId);
}

