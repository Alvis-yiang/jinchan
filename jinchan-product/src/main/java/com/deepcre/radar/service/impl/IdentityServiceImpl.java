package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.IdentityDao;
import com.deepcre.radar.entity.IdentityEntity;
import com.deepcre.radar.service.IdentityService;


@Service("identityService")
public class IdentityServiceImpl extends ServiceImpl<IdentityDao, IdentityEntity> implements IdentityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<IdentityEntity> page = this.page(
                new Query<IdentityEntity>().getPage(params),
                new QueryWrapper<IdentityEntity>()
        );

        return new PageUtils(page);
    }

}