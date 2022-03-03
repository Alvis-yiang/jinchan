package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.StockinEntity;
import com.deepcre.radar.vo.StockinReqVo;

import java.util.List;
import java.util.Map;

/**
 * 入库表
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface StockinService extends IService<StockinEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map isExit(Map<String,Object> params);

    //入库，如果是产品，则返回产品编号
    List stockin(StockinReqVo stockinReqVo);
}

