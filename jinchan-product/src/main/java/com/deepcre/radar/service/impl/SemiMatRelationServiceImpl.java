package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.SemiMatRelationDao;
import com.deepcre.radar.entity.SemiMatRelationEntity;
import com.deepcre.radar.service.SemiMatRelationService;


@Service("semiMatRelationService")
public class SemiMatRelationServiceImpl extends ServiceImpl<SemiMatRelationDao, SemiMatRelationEntity> implements SemiMatRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SemiMatRelationEntity> page = this.page(
                new Query<SemiMatRelationEntity>().getPage(params),
                new QueryWrapper<SemiMatRelationEntity>()
        );

        return new PageUtils(page);
    }

}