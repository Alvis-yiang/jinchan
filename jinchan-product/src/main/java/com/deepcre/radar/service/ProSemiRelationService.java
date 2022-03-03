package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProSemiRelationEntity;
import com.deepcre.radar.vo.ProRelationReqVo;

import java.util.Map;

/**
 * 产品半成品关系表：一种产品对应多种半成品
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface ProSemiRelationService extends IService<ProSemiRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByTree(Map<String,Object> params);

    void saveRelation(ProRelationReqVo proRelationReqVo);
}

