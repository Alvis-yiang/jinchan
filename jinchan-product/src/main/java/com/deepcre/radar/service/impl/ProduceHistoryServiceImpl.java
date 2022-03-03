package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProduceHistoryDao;
import com.deepcre.radar.entity.ProduceHistoryEntity;
import com.deepcre.radar.service.ProduceHistoryService;


@Service("produceHistoryService")
public class ProduceHistoryServiceImpl extends ServiceImpl<ProduceHistoryDao, ProduceHistoryEntity> implements ProduceHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProduceHistoryEntity> page = this.page(
                new Query<ProduceHistoryEntity>().getPage(params),
                new QueryWrapper<ProduceHistoryEntity>()
        );

        return new PageUtils(page);
    }

}