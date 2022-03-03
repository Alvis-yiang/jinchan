package com.deepcre.radar.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.deepcre.radar.dao.*;
import com.deepcre.radar.entity.*;
import com.deepcre.radar.vo.ApplyStockoutRespVo;
import com.deepcre.radar.vo.KeepingInfoRespVo;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.service.StockoutService;
import org.springframework.transaction.annotation.Transactional;


@Service("stockoutService")
public class StockoutServiceImpl extends ServiceImpl<StockoutDao, StockoutEntity> implements StockoutService {

    @Autowired
    KeepingAmountDao keepingAmountDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    SemimanufacturesDao semimanufacturesDao;

    @Autowired
    MaterialDao materialDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockoutEntity> page = this.page(
                new Query<StockoutEntity>().getPage(params),
                new QueryWrapper<StockoutEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 提交出库请求
     *
     * @param stockout
     * @return
     */
    @Override
    @Transactional
    public Boolean applyStockout(StockoutEntity stockout) {
        //1.检查库存是否足够
        Integer count = stockout.getCount();
        KeepingAmountEntity keepingAmountEntity = keepingAmountDao.selectOne(new QueryWrapper<KeepingAmountEntity>()
                .eq("cat_id", stockout.getCatId())
                .eq("goods_id", stockout.getGoodsId()));
        if (keepingAmountEntity.getAmount() < count) {
            return false;
        }
        //2.将出库信息插入出库表，出库状态为：1（提交请求）
        stockout.setApplyDate(new Date());
        this.save(stockout);
        //3.更新库存数量
        KeepingAmountEntity updateKeepingAmount = new KeepingAmountEntity();
        updateKeepingAmount.setAmount(keepingAmountEntity.getAmount() - stockout.getCount());
        keepingAmountDao.update(updateKeepingAmount, new QueryWrapper<KeepingAmountEntity>()
                .eq("cat_id", stockout.getCatId()).eq("goods_id", stockout.getGoodsId()));
        return true;
    }

    /**
     * 根据用户id，查询该用户的所有出库请求
     *
     * @param
     * @return
     */
    @Override
    public List<ApplyStockoutRespVo> queryPageByUser(Map<String, Object> params) {
        QueryWrapper<StockoutEntity> queryWrapper = new QueryWrapper<>();
        //请求的用户id
        Long empid = Long.valueOf((String) params.get("empid"));
        queryWrapper.eq("empid", empid).eq("state", 1);
        //类型id：catId
        String cat = (String) params.get("catId");
        Long catId ;
        if (StringUtils.isNotEmpty(cat)){
            catId = Long.valueOf(cat);
            queryWrapper.eq("cat_id", catId);
        }
        //出库品名称
        String name = (String) params.get("name");
        if (StringUtils.isNotEmpty(name)) {
            //查询产品名称符合的数据
            List<ProductEntity> product = productDao.selectList(new QueryWrapper<ProductEntity>().like("product_name", name));
            if (!product.isEmpty()){
                queryWrapper.and(wrapper -> {
                    for (ProductEntity productEntity : product) {
                        wrapper.or(queryWrapper1 -> {
                            queryWrapper1.eq("cat_id", 1).eq("goods_id", productEntity.getProductId());
                        });
                    }
                });
            }
            //查询半成品名称符合的数据
            List<SemimanufacturesEntity> semi = semimanufacturesDao.selectList(new QueryWrapper<SemimanufacturesEntity>().like("semi_name", name));
            if (!semi.isEmpty()){
                queryWrapper.and(wrapper -> {
                    for (SemimanufacturesEntity semimanufacturesEntity : semi) {
                        wrapper.or(queryWrapper1 -> {
                            queryWrapper1.eq("cat_id",2).eq("goods_id",semimanufacturesEntity.getSemiId());
                        });
                    }
                });
            }
            //查询原料名称符合的数据
            List<MaterialEntity> mat = materialDao.selectList(new QueryWrapper<MaterialEntity>().like("mat_name", name));
            if (!mat.isEmpty()){
                queryWrapper.and(wrapper ->{
                    for (MaterialEntity material: mat) {
                        wrapper.or(queryWrapper1 ->{
                            queryWrapper1.eq("cat_id",3).eq("goods_id",material.getMatId());
                        });
                    }
                });
            }
        }
        //出库品型号
        String model = (String) params.get("model");
        if (StringUtils.isNotEmpty(model)){
            //查询产品型号符合的数据
            List<ProductEntity> product = productDao.selectList(new QueryWrapper<ProductEntity>().like("product_model", model));
            if (!product.isEmpty()){
                queryWrapper.and(wrapper -> {
                    for (ProductEntity productEntity : product) {
                        wrapper.or(queryWrapper1 -> {
                            queryWrapper1.eq("cat_id", 1).eq("goods_id", productEntity.getProductId());
                        });
                    }
                });
            }
            //查询半成品型号符合的数据
            List<SemimanufacturesEntity> semi = semimanufacturesDao.selectList(new QueryWrapper<SemimanufacturesEntity>().like("semi_model", model));
            if (!semi.isEmpty()){
                queryWrapper.and(wrapper -> {
                    for (SemimanufacturesEntity semimanufacturesEntity : semi) {
                        wrapper.or(queryWrapper1 -> {
                            queryWrapper1.eq("cat_id",2).eq("goods_id",semimanufacturesEntity.getSemiId());
                        });
                    }
                });
            }
            //查询原料型号符合的数据
            List<MaterialEntity> mat = materialDao.selectList(new QueryWrapper<MaterialEntity>().like("mat_model", model));
            if (!mat.isEmpty()){
                queryWrapper.and(wrapper ->{
                    for (MaterialEntity material: mat) {
                        wrapper.or(queryWrapper1 ->{
                            queryWrapper1.eq("cat_id",3).eq("goods_id",material.getMatId());
                        });
                    }
                });
            }
        }

        List<StockoutEntity> records = this.baseMapper.selectList(queryWrapper);

        //没有记录就无需再查了
        if (records.isEmpty()) {
            List<ApplyStockoutRespVo> respVos = new ArrayList<>();
            return respVos;
        }
        //有记录的话，将每一条记录设置出库品类型名称和出库品名称,单位，型号，描述
        List<ApplyStockoutRespVo> list = records.stream().map(stockoutEntity -> {
            ApplyStockoutRespVo applyStockoutRespVo = new ApplyStockoutRespVo();
            BeanUtils.copyProperties(stockoutEntity, applyStockoutRespVo);
            if (stockoutEntity.getCatId() == 1) {//产品
                ProductEntity productEntity = productDao.selectById(stockoutEntity.getGoodsId());
                applyStockoutRespVo.setCatName("成品");
                applyStockoutRespVo.setGoodsName(productEntity.getProductName());
                applyStockoutRespVo.setUnit(productEntity.getProductUnit());
                applyStockoutRespVo.setModel(productEntity.getProductModel());
                applyStockoutRespVo.setDescription(productEntity.getProductDescription());
            } else if (stockoutEntity.getCatId() == 2) {//半成品
                SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(stockoutEntity.getGoodsId());
                applyStockoutRespVo.setCatName("半成品");
                applyStockoutRespVo.setGoodsName(semimanufacturesEntity.getSemiName());
                applyStockoutRespVo.setUnit(semimanufacturesEntity.getSemiUnit());
                applyStockoutRespVo.setModel(semimanufacturesEntity.getSemiModel());
                applyStockoutRespVo.setDescription(semimanufacturesEntity.getSemiDescription());
            } else {//原料
                MaterialEntity materialEntity = materialDao.selectById(stockoutEntity.getGoodsId());
                applyStockoutRespVo.setCatName("原料");
                applyStockoutRespVo.setGoodsName(materialEntity.getMatName());
                applyStockoutRespVo.setUnit(materialEntity.getMatUnit());
                applyStockoutRespVo.setModel(materialEntity.getMatModel());
                applyStockoutRespVo.setDescription(materialEntity.getMatDescription());
            }
            return applyStockoutRespVo;
        }).collect(Collectors.toList());
        return list;
    }

}