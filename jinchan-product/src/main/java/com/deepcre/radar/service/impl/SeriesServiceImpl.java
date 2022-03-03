package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.ProductDao;
import com.deepcre.radar.entity.ProductEntity;
import com.deepcre.radar.entity.StorageEntity;
import com.deepcre.radar.vo.SeriesAndProductRespVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.SeriesDao;
import com.deepcre.radar.entity.SeriesEntity;
import com.deepcre.radar.service.SeriesService;


@Service("seriesService")
public class SeriesServiceImpl extends ServiceImpl<SeriesDao, SeriesEntity> implements SeriesService {

    @Autowired
    ProductDao productDao;

    @Autowired
    SeriesDao seriesDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeriesEntity> page = this.page(
                new Query<SeriesEntity>().getPage(params),
                new QueryWrapper<SeriesEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 列表：查出所有系列，以及系列下对应的产品，产品下对应的所有型号
     */
    @Override
    public List queryPageByTree(Map<String, Object> params) {
        //1、查询所有系列,以及产品名称，产品id，型号
        List<ProductEntity> productEntities = productDao.selectList(new QueryWrapper<ProductEntity>()
                .select("series_id","GROUP_CONCAT(product_name) product_name","GROUP_CONCAT(product_model) product_model").groupBy("series_id"));
        //2、处理数据

        List<SeriesAndProductRespVo> collect = productEntities.stream().map((productEntity) -> {
            //2.1 根据系列id查询系列名称
            SeriesEntity seriesEntity = seriesDao.selectById(productEntity.getSeriesId());
            //2.2 处理产品名称和型号
            String[] productNames = productEntity.getProductName().split(",");
            String[] productModels = productEntity.getProductModel().split(",");

            List children = new ArrayList();

            for (int i = 0; i < productNames.length; i++) {
                Map productAndModel = new HashMap<>();
                productAndModel.put("key",i);
                productAndModel.put("productName", productNames[i]);
                productAndModel.put("productModel", productModels[i]);
                children.add(productAndModel);
            }


            //
            SeriesAndProductRespVo seriesAndProductRespVo = new SeriesAndProductRespVo();
            seriesAndProductRespVo.setSeriesId(productEntity.getSeriesId());//设置系列id
            seriesAndProductRespVo.setSeriesName(seriesEntity.getSeriesName());//设置系列名称
            seriesAndProductRespVo.setChildren(children);//设置产品名称和型号
            return seriesAndProductRespVo;
        }).collect(Collectors.toList());

        return collect;
    }

//    List<StorageEntity> level1Stores = storageEntities.stream().filter(storageEntity ->
//            storageEntity.getParentId() == 0
//    ).map((store)->{
//        store.setChildren(getChildrens(store,storageEntities));
//        return store;
//    }).collect(Collectors.toList());

}