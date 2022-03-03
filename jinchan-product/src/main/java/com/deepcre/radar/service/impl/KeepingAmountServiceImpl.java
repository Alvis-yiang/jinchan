package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.MaterialDao;
import com.deepcre.radar.dao.ProductDao;
import com.deepcre.radar.dao.SemimanufacturesDao;
import com.deepcre.radar.entity.*;
import com.deepcre.radar.vo.KeepingRespVo;
import com.deepcre.radar.vo.ProductRespVo;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.KeepingAmountDao;
import com.deepcre.radar.service.KeepingAmountService;
import org.springframework.transaction.annotation.Transactional;


@Service("keepingAmountService")
public class KeepingAmountServiceImpl extends ServiceImpl<KeepingAmountDao, KeepingAmountEntity> implements KeepingAmountService {

    @Autowired
    ProductDao productDao;

    @Autowired
    SemimanufacturesDao semimanufacturesDao;

    @Autowired
    MaterialDao materialDao;

    @Transactional
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<KeepingAmountEntity> page = this.page(
                new Query<KeepingAmountEntity>().getPage(params),
                new QueryWrapper<KeepingAmountEntity>()
        );
        PageUtils pageUtils = new PageUtils(page);
        List<KeepingAmountEntity> records = page.getRecords();
        List<KeepingRespVo> respVos = records.stream().map((keepingAmountEntity)->{
            KeepingRespVo keepingRespVo = new KeepingRespVo();

            try {
                BeanUtils.copyProperties(keepingRespVo,keepingAmountEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //设置库存品的名称、型号、描述信息、单位
            ProductEntity productEntity = productDao.selectById(keepingAmountEntity.getGoodsId());
            if (null != productEntity){
                keepingRespVo.setName(productEntity.getProductName());
                keepingRespVo.setModel(productEntity.getProductModel());
                keepingRespVo.setDescription(productEntity.getProductDescription());
                keepingRespVo.setUnit(productEntity.getProductUnit());

            }

            return keepingRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    /**
     * 获取产品库存
     * @param params
     * @return
     */
    @Transactional
    @Override
    public PageUtils queryProductPage(Map<String, Object> params) {
        QueryWrapper<KeepingAmountEntity> queryWrapper = new QueryWrapper<KeepingAmountEntity>();
        queryWrapper.eq("cat_id", 1);
        //如果key有内容，则需要根据key的内容，对名称和型号进行模糊查询
//        System.out.println("获得的key："+params.get("key"));
        if(null != params.get("key") && params.get("key") != ""){
            String par = (String) params.get("key");
            //根据参数内容查询产品信息表，获取产品的id列表
            QueryWrapper<ProductEntity> wrapper = new QueryWrapper<ProductEntity>().select("product_id").like("product_name", par).or().like("product_model", par);
            List<Long> ids = productDao.selectList(wrapper).stream().map((productEntitie)->{
                return productEntitie.getProductId();
            }).collect(Collectors.toList());

//            System.out.println("canshuhuoqu 产品"+ids);
            queryWrapper.and(wrapper1->{
                for (int i = 0; i < ids.size(); i++) {
                    wrapper1.or().eq("goods_id",ids.get(i));
                }
            });


        }

        IPage<KeepingAmountEntity> page = this.page(
                new Query<KeepingAmountEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<KeepingAmountEntity> records = page.getRecords();
        //没有记录就无需再查了
        if (records.isEmpty()){
            List<KeepingRespVo> respVos = new ArrayList<>();
            pageUtils.setList(respVos);
            return pageUtils;
        }
        List<KeepingRespVo> respVos = records.stream().map((keepingAmountEntity)->{
            KeepingRespVo keepingRespVo = new KeepingRespVo();

            try {
                BeanUtils.copyProperties(keepingRespVo,keepingAmountEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //设置产品库存的名称、型号、描述信息、单位
            ProductEntity productEntity = productDao.selectById(keepingAmountEntity.getGoodsId());
            if (null != productEntity){
                keepingRespVo.setName(productEntity.getProductName());
                keepingRespVo.setModel(productEntity.getProductModel());
                keepingRespVo.setDescription(productEntity.getProductDescription());
                keepingRespVo.setUnit(productEntity.getProductUnit());
            }

            return keepingRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    /**
     * 获取半成品库存
     * @param params
     * @return
     */
    @Transactional
    @Override
    public PageUtils querySemiPage(Map<String, Object> params) {
        QueryWrapper<KeepingAmountEntity> queryWrapper = new QueryWrapper<KeepingAmountEntity>();
        queryWrapper.eq("cat_id", 2);
        //如果key有内容，则需要根据key的内容，对名称和型号进行模糊查询
//        System.out.println("获得的key："+params.get("key"));
        if(null != params.get("key") && params.get("key") != ""){
            String par = (String) params.get("key");
            //根据参数内容查询产品信息表，获取产品的id列表
            QueryWrapper<SemimanufacturesEntity> wrapper = new QueryWrapper<SemimanufacturesEntity>().select("semi_id").like("semi_name", par).or().like("semi_model", par);
            List<Long> ids = semimanufacturesDao.selectList(wrapper).stream().map((semimanufacturesEntity)->{
                return semimanufacturesEntity.getSemiId();
            }).collect(Collectors.toList());
            queryWrapper.and(wrapper1 ->{
                for (int i = 0; i < ids.size(); i++) {
                    wrapper1.or().eq("goods_id",ids.get(i));
                }
            });


        }

        IPage<KeepingAmountEntity> page = this.page(
                new Query<KeepingAmountEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<KeepingAmountEntity> records = page.getRecords();
        //没有记录就无需再查了
        if (records.isEmpty()){
            List<KeepingRespVo> respVos = new ArrayList<>();
            pageUtils.setList(respVos);
            return pageUtils;
        }
        List<KeepingRespVo> respVos = records.stream().map((keepingAmountEntity)->{
            KeepingRespVo keepingRespVo = new KeepingRespVo();

            try {
                BeanUtils.copyProperties(keepingRespVo,keepingAmountEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //设置半成品库存的名称、型号、描述信息、单位
            SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(keepingAmountEntity.getGoodsId());
            if (null != semimanufacturesEntity){
                keepingRespVo.setName(semimanufacturesEntity.getSemiName());
                keepingRespVo.setModel(semimanufacturesEntity.getSemiModel());
                keepingRespVo.setDescription(semimanufacturesEntity.getSemiDescription());
                keepingRespVo.setUnit(semimanufacturesEntity.getSemiUnit());
            }
            return keepingRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    /**
     * 获取原料库存
     * @param params
     * @return
     */
    @Transactional
    @Override
    public PageUtils queryMaterialPage(Map<String, Object> params) {
        QueryWrapper<KeepingAmountEntity> queryWrapper = new QueryWrapper<KeepingAmountEntity>();
        queryWrapper.eq("cat_id", 3);
        //如果key有内容，则需要根据key的内容，对名称和型号进行模糊查询
//        System.out.println("获得的key："+params.get("key"));
        if(null != params.get("key") && params.get("key") != ""){
            String par = (String) params.get("key");
            //根据参数内容查询产品信息表，获取产品的id列表
            QueryWrapper<MaterialEntity> wrapper = new QueryWrapper<MaterialEntity>().select("mat_id").like("mat_name", par).or().like("mat_model", par);
            List<Long> ids = materialDao.selectList(wrapper).stream().map((materialEntity)->{
                return materialEntity.getMatId();
            }).collect(Collectors.toList());

            queryWrapper.and(wrapper1 ->{
                for (int i = 0; i < ids.size(); i++) {
                    wrapper1.or().eq("goods_id",ids.get(i));
                }
            });

        }

        IPage<KeepingAmountEntity> page = this.page(
                new Query<KeepingAmountEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<KeepingAmountEntity> records = page.getRecords();
        //没有记录就无需再查了
        if (records.isEmpty()){
            List<KeepingRespVo> respVos = new ArrayList<>();
            pageUtils.setList(respVos);
            return pageUtils;
        }
        List<KeepingRespVo> respVos = records.stream().map((keepingAmountEntity)->{
            KeepingRespVo keepingRespVo = new KeepingRespVo();

            try {
                BeanUtils.copyProperties(keepingRespVo,keepingAmountEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //设置原料库存的名称、型号、描述信息、单位
            MaterialEntity materialEntity = materialDao.selectById(keepingAmountEntity.getGoodsId());
            if (null != materialEntity){
                keepingRespVo.setName(materialEntity.getMatName());
                keepingRespVo.setModel(materialEntity.getMatModel());
                keepingRespVo.setDescription(materialEntity.getMatDescription());
                keepingRespVo.setUnit(materialEntity.getMatUnit());
            }

            return keepingRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }




}