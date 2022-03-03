package com.deepcre.radar.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.StockoutOrderPrintDao;
import com.deepcre.radar.entity.StockoutOrderPrintEntity;
import com.deepcre.radar.service.StockoutOrderPrintService;


@Service("stockoutOrderPrintService")
public class StockoutOrderPrintServiceImpl extends ServiceImpl<StockoutOrderPrintDao, StockoutOrderPrintEntity> implements StockoutOrderPrintService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockoutOrderPrintEntity> page = this.page(
                new Query<StockoutOrderPrintEntity>().getPage(params),
                new QueryWrapper<StockoutOrderPrintEntity>()
        );

        return new PageUtils(page);
    }

}