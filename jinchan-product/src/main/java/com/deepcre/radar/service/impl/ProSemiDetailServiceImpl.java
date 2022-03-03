package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProSemiDetailDao;
import com.deepcre.radar.entity.ProSemiDetailEntity;
import com.deepcre.radar.service.ProSemiDetailService;


@Service("proSemiDetailService")
public class ProSemiDetailServiceImpl extends ServiceImpl<ProSemiDetailDao, ProSemiDetailEntity> implements ProSemiDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProSemiDetailEntity> page = this.page(
                new Query<ProSemiDetailEntity>().getPage(params),
                new QueryWrapper<ProSemiDetailEntity>()
        );

        return new PageUtils(page);
    }

}