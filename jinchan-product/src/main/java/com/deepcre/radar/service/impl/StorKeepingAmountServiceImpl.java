package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.StorKeepingAmountDao;
import com.deepcre.radar.entity.StorKeepingAmountEntity;
import com.deepcre.radar.service.StorKeepingAmountService;


@Service("storKeepingAmountService")
public class StorKeepingAmountServiceImpl extends ServiceImpl<StorKeepingAmountDao, StorKeepingAmountEntity> implements StorKeepingAmountService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StorKeepingAmountEntity> page = this.page(
                new Query<StorKeepingAmountEntity>().getPage(params),
                new QueryWrapper<StorKeepingAmountEntity>()
        );

        return new PageUtils(page);
    }

}