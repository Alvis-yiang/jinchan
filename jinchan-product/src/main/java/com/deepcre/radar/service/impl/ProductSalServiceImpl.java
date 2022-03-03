package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.KeepingDao;
import com.deepcre.radar.dao.ProductDao;
import com.deepcre.radar.dao.StorageDao;
import com.deepcre.radar.entity.KeepingEntity;
import com.deepcre.radar.entity.ProductEntity;
import com.deepcre.radar.entity.StorageEntity;
import com.deepcre.radar.vo.KeepingInfoRespVo;
import com.deepcre.radar.vo.ProductSalRespVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProductSalDao;
import com.deepcre.radar.entity.ProductSalEntity;
import com.deepcre.radar.service.ProductSalService;


@Service("productSalService")
public class ProductSalServiceImpl extends ServiceImpl<ProductSalDao, ProductSalEntity> implements ProductSalService {
    @Autowired
    ProductDao productDao;

    @Autowired
    StorageDao storageDao;

    @Autowired
    KeepingDao keepingDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductSalEntity> page = this.page(
                new Query<ProductSalEntity>().getPage(params),
                new QueryWrapper<ProductSalEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageInfo(Map<String, Object> params) {
        String name = (String) params.get("selectName");//名称
        String model = (String) params.get("selectModel");//型号
        String no = (String) params.get("selectNo");//产品编号

        //1、查询产品条件
        QueryWrapper<ProductEntity> productWrapper = new QueryWrapper<ProductEntity>().select("product_id");
        if (!StringUtils.isNullOrEmpty(name)) {
            productWrapper.like("product_name", name);
        }
        if (!StringUtils.isNullOrEmpty(model)) {
            productWrapper.like("product_model", model);
        }
        List<Long> productIds = productDao.selectList(productWrapper).stream().map((productEntity) -> {
            return productEntity.getProductId();
        }).collect(Collectors.toList());

        QueryWrapper<ProductSalEntity> productSalEntityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(no)){
            for (int i = 0; i < productIds.size(); i++) {
                int finalI = i;
                productSalEntityQueryWrapper.or((wrapper) -> {
                    wrapper.eq("product_id", productIds.get(finalI)).eq("product_no",no).eq("sal_flag",1);
                });
            }
        }else{
            for (int i = 0; i < productIds.size(); i++) {
                int finalI = i;
                productSalEntityQueryWrapper.or((wrapper) -> {
                    wrapper.eq("product_id", productIds.get(finalI)).eq("sal_flag",1);
                });
            }
        }
        IPage<ProductSalEntity> page = this.page(
                new Query<ProductSalEntity>().getPage(params),
                productSalEntityQueryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<ProductSalEntity> records = page.getRecords();

        //没有记录就无需再查了
        if (records.isEmpty()) {
            List<ProductSalRespVo> respVos = new ArrayList<>();
            pageUtils.setList(respVos);
            return pageUtils;
        }
        List<ProductSalRespVo> respVos = records.stream().map((productSalEntity) -> {
            ProductSalRespVo productSalRespVo = new ProductSalRespVo();
            ProductEntity productEntity = productDao.selectOne(new QueryWrapper<ProductEntity>().eq("product_id", productSalEntity.getProductId()));
            BeanUtils.copyProperties(productSalEntity,productSalRespVo);
            BeanUtils.copyProperties(productEntity,productSalRespVo);
            //查询存放位置
            String storname = "";
            KeepingEntity keepingEntity = keepingDao.selectOne(new QueryWrapper<KeepingEntity>().eq("identity_id", productSalEntity.getIdentityId()));

            StorageEntity storageEntity = storageDao.selectById(keepingEntity.getStorId());
            while (storageEntity.getParentId() != 0) {
                storname = storageEntity.getStorName() + storname;
                storageEntity = storageDao.selectById(storageEntity.getParentId());
            }
            storname = storageEntity.getStorName() + storname;
            productSalRespVo.setStorName(storname);

            return productSalRespVo;
        }).collect(Collectors.toList());


        pageUtils.setList(respVos);

        return pageUtils;
    }

}