package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.*;
import com.deepcre.radar.entity.*;
import com.deepcre.radar.vo.ProRelationReqVo;
import com.mysql.cj.util.StringUtils;
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

import com.deepcre.radar.service.ProComposeService;
import org.springframework.transaction.annotation.Transactional;


@Service("proComposeService")
public class ProComposeServiceImpl extends ServiceImpl<ProComposeDao, ProComposeEntity> implements ProComposeService {

    @Autowired
    ProductDao productDao;

    @Autowired
    SemimanufacturesDao semimanufacturesDao;

    @Autowired
    MaterialDao materialDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProComposeEntity> page = this.page(
                new Query<ProComposeEntity>().getPage(params),
                new QueryWrapper<ProComposeEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *
     * @param
     */
    @Override
    @Transactional
    public List<ProComposeEntity> listWithTree(Map<String, Object> params) {
        String productName = (String) params.get("productName");
        String productModel = (String) params.get("productModel");
        QueryWrapper<ProductEntity> productWrapper = new QueryWrapper<>();
        if (null != params.get("seriesId") ){
            productWrapper.eq("series_id",params.get("seriesId"));
        }
        if (!StringUtils.isNullOrEmpty(productName)){
            productWrapper.like("product_name",productName);
        }
        if (!StringUtils.isNullOrEmpty(productModel)){
            productWrapper.like("product_model",productModel);
        }
        List ids = new ArrayList();
        if (null != productWrapper) {
            productDao.selectList(productWrapper).stream()
                    .map(productEntity -> {
                        ids.add(productEntity.getProductId());
                        return null;
                    }).collect(Collectors.toList());
        }

        //1.查出所有
        List<ProComposeEntity> proComposeEntities = baseMapper.selectList(null);

        if (ids.isEmpty()){
            //2.组装成树形结构
            List<ProComposeEntity> list = proComposeEntities.stream().filter(proComposeEntity ->
                    proComposeEntity.getLevel() == 0
            ).map(proCompose -> {
                ProductEntity productEntity = productDao.selectById(proCompose.getId());
                proCompose.setName(productEntity.getProductName());
                proCompose.setModel(productEntity.getProductModel());
                proCompose.setChildren(getChildrens(proCompose, proComposeEntities));
                return proCompose;
            }).collect(Collectors.toList());

            return list;
        }

        //2.组装成树形结构
        List<ProComposeEntity> list = proComposeEntities.stream().filter(proComposeEntity ->
            proComposeEntity.getLevel() == 0 && ids.contains(proComposeEntity.getId())
        ).map(proCompose -> {
            ProductEntity productEntity = productDao.selectById(proCompose.getId());
            proCompose.setName(productEntity.getProductName());
            proCompose.setModel(productEntity.getProductModel());
            proCompose.setChildren(getChildrens(proCompose, proComposeEntities));
            return proCompose;
        }).collect(Collectors.toList());

        return list;
    }

    //递归查找所有产品的子部件
    private List<ProComposeEntity> getChildrens(ProComposeEntity root, List<ProComposeEntity> all){
        List<ProComposeEntity> children = all.stream().filter(proComposeEntity -> {
            return proComposeEntity.getParentRelId() == root.getRelId();
        }).map(proComposeEntity -> {
            Integer level = proComposeEntity.getLevel();
            if (level==1){
                SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(proComposeEntity.getId());
                proComposeEntity.setName(semimanufacturesEntity.getSemiName());
                proComposeEntity.setModel(semimanufacturesEntity.getSemiModel());
            }else if (level == 2){
                MaterialEntity materialEntity = materialDao.selectById(proComposeEntity.getId());
                proComposeEntity.setName(materialEntity.getMatName());
                proComposeEntity.setModel(materialEntity.getMatModel());
            }

            //找到子菜单
            proComposeEntity.setChildren(getChildrens(proComposeEntity,all));
            return proComposeEntity;
        }).collect(Collectors.toList());

        return children;

    }

}