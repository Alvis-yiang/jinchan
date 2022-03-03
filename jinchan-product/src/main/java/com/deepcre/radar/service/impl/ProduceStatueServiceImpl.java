package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProduceStatueDao;
import com.deepcre.radar.entity.ProduceStatueEntity;
import com.deepcre.radar.service.ProduceStatueService;


@Service("produceStatueService")
public class ProduceStatueServiceImpl extends ServiceImpl<ProduceStatueDao, ProduceStatueEntity> implements ProduceStatueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProduceStatueEntity> page = this.page(
                new Query<ProduceStatueEntity>().getPage(params),
                new QueryWrapper<ProduceStatueEntity>()
        );

        return new PageUtils(page);
    }

}