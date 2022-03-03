package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.StockoutOrderDetailDao;
import com.deepcre.radar.entity.StockoutOrderDetailEntity;
import com.deepcre.radar.service.StockoutOrderDetailService;


@Service("stockoutOrderDetailService")
public class StockoutOrderDetailServiceImpl extends ServiceImpl<StockoutOrderDetailDao, StockoutOrderDetailEntity> implements StockoutOrderDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockoutOrderDetailEntity> page = this.page(
                new Query<StockoutOrderDetailEntity>().getPage(params),
                new QueryWrapper<StockoutOrderDetailEntity>()
        );

        return new PageUtils(page);
    }

}