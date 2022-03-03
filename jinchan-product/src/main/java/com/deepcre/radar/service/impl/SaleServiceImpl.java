package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.KeepingAmountDao;
import com.deepcre.radar.dao.KeepingDao;
import com.deepcre.radar.dao.ProductSalDao;
import com.deepcre.radar.entity.*;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.SaleDao;
import com.deepcre.radar.service.SaleService;
import org.springframework.transaction.annotation.Transactional;


@Service("saleService")
public class SaleServiceImpl extends ServiceImpl<SaleDao, SaleEntity> implements SaleService {

    @Autowired
    KeepingDao keepingDao;

    @Autowired
    KeepingAmountDao keepingAmountDao;

    @Autowired
    ProductSalDao productSalDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SaleEntity> page = this.page(
                new Query<SaleEntity>().getPage(params),
                new QueryWrapper<SaleEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询销售记录具体信息
     * @param params
     * @return
     */
    @Override
    public PageUtils queryListPage(Map<String, Object> params) {
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


        return null;
    }

    /**
     * 销售
     * @param saleEntity
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void sale(SaleEntity saleEntity) {
        //1、删除库存表内该库存信息
        keepingDao.delete(new QueryWrapper<KeepingEntity>().eq("identity_id",saleEntity.getIdentityId()));
        //2、更新库存量表内库存数量
        KeepingAmountEntity keepingAmountEntity = keepingAmountDao.selectOne(new QueryWrapper<KeepingAmountEntity>().eq("cat_id",1)
                .eq("goods_id",saleEntity.getProductId()));
        keepingAmountEntity.setAmount(keepingAmountEntity.getAmount()-1);
        keepingAmountDao.update(keepingAmountEntity,new QueryWrapper<KeepingAmountEntity>().eq("cat_id",1).eq("goods_id",saleEntity.getProductId()));
        //3、更新产品销售表内销售状态为0
        ProductSalEntity productSalEntity = new ProductSalEntity();
        productSalEntity.setSalFlag(0);
        productSalDao.update(productSalEntity,new QueryWrapper<ProductSalEntity>().eq("identity_id",saleEntity.getIdentityId()));
        //4、销售表添加记录
        this.save(saleEntity);
    }

}













