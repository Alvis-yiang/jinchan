package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProComposeEntity;
import com.deepcre.radar.vo.ProRelationReqVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-28 11:23:13
 */
public interface ProComposeService extends IService<ProComposeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ProComposeEntity> listWithTree(Map<String, Object> params);
}

