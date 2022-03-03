package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.SemiMatDetailDao;
import com.deepcre.radar.entity.SemiMatDetailEntity;
import com.deepcre.radar.service.SemiMatDetailService;


@Service("semiMatDetailService")
public class SemiMatDetailServiceImpl extends ServiceImpl<SemiMatDetailDao, SemiMatDetailEntity> implements SemiMatDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SemiMatDetailEntity> page = this.page(
                new Query<SemiMatDetailEntity>().getPage(params),
                new QueryWrapper<SemiMatDetailEntity>()
        );

        return new PageUtils(page);
    }

}