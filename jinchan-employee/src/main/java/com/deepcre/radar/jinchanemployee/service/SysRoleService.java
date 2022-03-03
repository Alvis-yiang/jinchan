package com.deepcre.radar.jinchanemployee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.jinchanemployee.entity.SysRoleEntity;

import java.util.Map;

/**
 * 角色
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-18 17:04:27
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

