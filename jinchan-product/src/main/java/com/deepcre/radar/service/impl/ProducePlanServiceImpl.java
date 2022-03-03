package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProducePlanDao;
import com.deepcre.radar.entity.ProducePlanEntity;
import com.deepcre.radar.service.ProducePlanService;


@Service("producePlanService")
public class ProducePlanServiceImpl extends ServiceImpl<ProducePlanDao, ProducePlanEntity> implements ProducePlanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProducePlanEntity> page = this.page(
                new Query<ProducePlanEntity>().getPage(params),
                new QueryWrapper<ProducePlanEntity>()
        );

        return new PageUtils(page);
    }

}