package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.*;
import com.deepcre.radar.entity.*;
import com.deepcre.radar.vo.KeepingInfoRespVo;
import com.deepcre.radar.vo.KeepingRespVo;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.service.KeepingService;
import org.springframework.transaction.annotation.Transactional;


@Service("keepingService")
public class KeepingServiceImpl extends ServiceImpl<KeepingDao, KeepingEntity> implements KeepingService {

    @Autowired
    ProductDao productDao;

    @Autowired
    SemimanufacturesDao semimanufacturesDao;

    @Autowired
    MaterialDao materialDao;

    @Autowired
    IdentityDao identityDao;

    @Autowired
    StorageDao storageDao;

    @Autowired
    KeepingAmountDao keepingAmountDao;

    @Autowired
    SaleDao saleDao;

    @Autowired
    StockoutDao stockoutDao;

    @Autowired
    KeepingDao keepingDao;

    /**
     * 查询库存品的分页信息，有参数的话将根据参数模糊查询，否则查询全部
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("selectName");//名称
        String model = (String) params.get("selectModel");//型号

        //1、查询产品条件
        QueryWrapper<ProductEntity> productWrapper = new QueryWrapper<ProductEntity>().select("product_id");

        //2、查询半成品条件
        QueryWrapper<SemimanufacturesEntity> semimanufacturesWrapper = new QueryWrapper<SemimanufacturesEntity>().select("semi_id");

        //3、查询原料条件
        QueryWrapper<MaterialEntity> materialWrapper = new QueryWrapper<MaterialEntity>().select("mat_id");

        if (!StringUtils.isNullOrEmpty(name)) {
            productWrapper.like("product_name", name);
            semimanufacturesWrapper.like("semi_name", name);
            materialWrapper.like("mat_name", name);
        }
        if (!StringUtils.isNullOrEmpty(model)) {
            productWrapper.like("product_model", model);
            semimanufacturesWrapper.like("semi_model", model);
            materialWrapper.like("mat_model", model);
        }

        List<Long> productIds = productDao.selectList(productWrapper).stream().map((productEntity) -> {
            return productEntity.getProductId();
        }).collect(Collectors.toList());
        List<Long> semiIds = semimanufacturesDao.selectList(semimanufacturesWrapper).stream().map((semimanufacturesEntity) -> {
            return semimanufacturesEntity.getSemiId();
        }).collect(Collectors.toList());
        List<Long> matIds = materialDao.selectList(materialWrapper).stream().map((materialEntity) -> {
            return materialEntity.getMatId();
        }).collect(Collectors.toList());

        QueryWrapper<KeepingEntity> keepingEntityQueryWrapper = new QueryWrapper<>();
        for (int i = 0; i < productIds.size(); i++) {
            int finalI = i;
            keepingEntityQueryWrapper.or((wrapper) -> {
                wrapper.eq("cat_id", 1).eq("goods_id", productIds.get(finalI));
            });
        }
        for (int i = 0; i < semiIds.size(); i++) {
            int finalI = i;
            keepingEntityQueryWrapper.or((wrapper) -> {
                wrapper.eq("cat_id", 2).eq("goods_id", semiIds.get(finalI));
            });
        }
        for (int i = 0; i < matIds.size(); i++) {
            int finalI = i;
            keepingEntityQueryWrapper.or((wrapper) -> {
                wrapper.eq("cat_id", 3).eq("goods_id", matIds.get(finalI));
            });
        }
        //TODO
        // 按照类型、id、仓库id进行分组


        IPage<KeepingEntity> page = this.page(
                new Query<KeepingEntity>().getPage(params),
                keepingEntityQueryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<KeepingEntity> records = page.getRecords();

        //没有记录就无需再查了
        if (records.isEmpty()) {
            List<KeepingInfoRespVo> respVos = new ArrayList<>();
            pageUtils.setList(respVos);
            return pageUtils;
        }
        //
        List<KeepingInfoRespVo> respVos = records.stream().filter(keepingEntity -> {
            //过滤掉库存为0的库存品
            Long catId = keepingEntity.getCatId();
            Long goodsId = keepingEntity.getGoodsId();
            KeepingAmountEntity keepingAmountEntity = keepingAmountDao.selectOne(new QueryWrapper<KeepingAmountEntity>()
                    .eq("cat_id", catId)
                    .eq("goods_id", goodsId));
            return keepingAmountEntity.getAmount() != 0;
        }).map((keepingEntity) -> {

            Long catId = keepingEntity.getCatId();
            Long goodsId = keepingEntity.getGoodsId();
            KeepingAmountEntity keepingAmountEntity = keepingAmountDao.selectOne(new QueryWrapper<KeepingAmountEntity>()
                    .eq("cat_id", catId)
                    .eq("goods_id", goodsId));

            //库存不为0
            KeepingInfoRespVo keepingInfoRespVo = new KeepingInfoRespVo();

            try {
                BeanUtils.copyProperties(keepingInfoRespVo, keepingEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //设置库存品的名称、型号、描述信息、单位，库位等信息
            if (keepingEntity.getCatId() == 1) {//产品
                ProductEntity productEntity = productDao.selectById(keepingEntity.getGoodsId());
                if (null != productEntity) {
                    keepingInfoRespVo.setName(productEntity.getProductName());
                    keepingInfoRespVo.setModel(productEntity.getProductModel());
                    keepingInfoRespVo.setDescription(productEntity.getProductDescription());
                    keepingInfoRespVo.setUnit(productEntity.getProductUnit());
                }
                //设置产品编号
                IdentityEntity identityEntity = identityDao.selectById(keepingEntity.getIdentityId());
                keepingInfoRespVo.setProductNo(identityEntity.getProductNo());
            } else if (keepingEntity.getCatId() == 2) {//半成品
                SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(keepingEntity.getGoodsId());
                if (null != semimanufacturesEntity) {
                    keepingInfoRespVo.setName(semimanufacturesEntity.getSemiName());
                    keepingInfoRespVo.setModel(semimanufacturesEntity.getSemiModel());
                    keepingInfoRespVo.setDescription(semimanufacturesEntity.getSemiDescription());
                    keepingInfoRespVo.setUnit(semimanufacturesEntity.getSemiUnit());
                }
            } else if (keepingEntity.getCatId() == 3) {//原料
                MaterialEntity materialEntity = materialDao.selectById(keepingEntity.getGoodsId());
                if (null != materialEntity) {
                    keepingInfoRespVo.setName(materialEntity.getMatName());
                    keepingInfoRespVo.setModel(materialEntity.getMatModel());
                    keepingInfoRespVo.setDescription(materialEntity.getMatDescription());
                    keepingInfoRespVo.setUnit(materialEntity.getMatUnit());
                }
            }
            //设置仓库名称
            String storname = "";
            StorageEntity storageEntity = storageDao.selectById(keepingEntity.getStorId());
            while (storageEntity.getParentId() != 0) {
                storname = storageEntity.getStorName() + storname;
                storageEntity = storageDao.selectById(storageEntity.getParentId());
            }
            storname = storageEntity.getStorName() + storname;
            keepingInfoRespVo.setStorName(storname);


            return keepingInfoRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);

        return pageUtils;
    }

    /**
     * 根据传递的类别id和库存品id，查询库存品存放位置等具体信息
     */
    @Transactional
    @Override
    public PageUtils queryKeepingInfo(Map<String, Object> params, Long catId, Long goodsId) {
//        System.out.println("参数："+params);
//        System.out.println("catid"+catId);
//        System.out.println("goodsid"+goodsId);

        QueryWrapper<KeepingEntity> queryWrapper = new QueryWrapper<KeepingEntity>().eq("cat_id", catId).eq("goods_id", goodsId);
        IPage<KeepingEntity> page = this.page(
                new Query<KeepingEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<KeepingEntity> records = page.getRecords();

        //没有记录就无需再查了
        if (records.isEmpty()) {
            List<KeepingInfoRespVo> respVos = new ArrayList<>();
            pageUtils.setList(respVos);
            return pageUtils;
        }
        List<KeepingInfoRespVo> respVos = records.stream().map((keepingEntity) -> {
            KeepingInfoRespVo keepingInfoRespVo = new KeepingInfoRespVo();

            try {
                BeanUtils.copyProperties(keepingInfoRespVo, keepingEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //设置库存品的名称、型号、描述信息、单位，库位等信息
            if (catId == 1) {//产品
                ProductEntity productEntity = productDao.selectById(keepingEntity.getGoodsId());
                if (null != productEntity) {
                    keepingInfoRespVo.setName(productEntity.getProductName());
                    keepingInfoRespVo.setModel(productEntity.getProductModel());
                    keepingInfoRespVo.setDescription(productEntity.getProductDescription());
                    keepingInfoRespVo.setUnit(productEntity.getProductUnit());
                }
                //设置产品编号
                IdentityEntity identityEntity = identityDao.selectById(keepingEntity.getIdentityId());
                keepingInfoRespVo.setProductNo(identityEntity.getProductNo());
            } else if (catId == 2) {//半成品
                SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(keepingEntity.getGoodsId());
                if (null != semimanufacturesEntity) {
                    keepingInfoRespVo.setName(semimanufacturesEntity.getSemiName());
                    keepingInfoRespVo.setModel(semimanufacturesEntity.getSemiModel());
                    keepingInfoRespVo.setDescription(semimanufacturesEntity.getSemiDescription());
                    keepingInfoRespVo.setUnit(semimanufacturesEntity.getSemiUnit());
                }
            } else if (catId == 3) {//原料
                MaterialEntity materialEntity = materialDao.selectById(keepingEntity.getGoodsId());
                if (null != materialEntity) {
                    keepingInfoRespVo.setName(materialEntity.getMatName());
                    keepingInfoRespVo.setModel(materialEntity.getMatModel());
                    keepingInfoRespVo.setDescription(materialEntity.getMatDescription());
                    keepingInfoRespVo.setUnit(materialEntity.getMatUnit());
                }
            }

            //设置仓库名称
            String storname = "";
            StorageEntity storageEntity = storageDao.selectById(keepingEntity.getStorId());
            while (storageEntity.getParentId() != 0) {
                storname = storageEntity.getStorName() + storname;
                storageEntity = storageDao.selectById(storageEntity.getParentId());
            }
            storname = storageEntity.getStorName() + storname;
            keepingInfoRespVo.setStorName(storname);


            return keepingInfoRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    /**
     * 出库
     * @param params
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int stockout(Map<String, Object> params) {
        Long catId = Long.parseLong((String) params.get("catId"));
        Long goodsId = Long.parseLong((String) params.get("goodsId"));
        String identityId = (String) params.get("identityId");
        //1、判断出库的是否为产品，若为产品，先查看是否有销售记录，若无，则无法出库，先补全销售记录。
        if (catId == 1){
            //1.1 无销售记录
            SaleEntity saleEntity = saleDao.selectOne(new QueryWrapper<SaleEntity>().eq("identity_id", identityId));
            if (null == saleEntity){
                return 0;
            }
            //1.2 有销售记录，更新销售记录的出库时间，
            saleEntity.setOutDate(new Date());
            saleDao.updateById(saleEntity);

        }
        //2、更新出库表
        StockoutEntity stockoutEntity = new StockoutEntity();
        stockoutEntity.setCatId(catId);
        stockoutEntity.setGoodsId(goodsId);
//        stockoutEntity.setCount((double) 1);
        stockoutEntity.setOutDate(new Date());
        stockoutEntity.setEmpid((long) 1);
        stockoutDao.insert(stockoutEntity);
        //3、更新库存表
        keepingDao.delete(new QueryWrapper<KeepingEntity>().eq("identity_id",identityId));
        //4、更新库存量表
        KeepingAmountEntity keepingAmountEntity = keepingAmountDao.selectOne(new QueryWrapper<KeepingAmountEntity>().eq("cat_id", catId).eq("goods_id", goodsId));
        keepingAmountEntity.setAmount(keepingAmountEntity.getAmount()-1);
        keepingAmountDao.update(keepingAmountEntity,new QueryWrapper<KeepingAmountEntity>().eq("cat_id", catId).eq("goods_id", goodsId));
        return 1;
    }

}