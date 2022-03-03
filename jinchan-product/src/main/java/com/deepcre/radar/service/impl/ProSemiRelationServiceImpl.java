package com.deepcre.radar.service.impl;

import com.deepcre.radar.vo.ProRelationReqVo;
import com.deepcre.radar.vo.ProductRelationRespVo;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProSemiRelationDao;
import com.deepcre.radar.entity.ProSemiRelationEntity;
import com.deepcre.radar.service.ProSemiRelationService;
import org.springframework.transaction.annotation.Transactional;


@Service("proSemiRelationService")
public class ProSemiRelationServiceImpl extends ServiceImpl<ProSemiRelationDao, ProSemiRelationEntity> implements ProSemiRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProSemiRelationEntity> page = this.page(
                new Query<ProSemiRelationEntity>().getPage(params),
                new QueryWrapper<ProSemiRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查出所有产品以及对应需要的半成品，以树形结构组装起来
     */
    @Override
    public PageUtils queryPageByTree(Map<String, Object> params) {
        new ProductRelationRespVo();

        //1.查出所有产品和半成品的关系记录，以产品id分组
        QueryWrapper<ProSemiRelationEntity> proSemiRelationEntityQueryWrapper = new QueryWrapper<>();
//        proSemiRelationEntityQueryWrapper.
        this.page(new Query<ProSemiRelationEntity>().getPage(params),proSemiRelationEntityQueryWrapper);

        return null;
    }

    /**
     * 接收保存新增产品组成
     * @param proRelationReqVo
     */
    @Override
    @Transactional
    public void saveRelation(ProRelationReqVo proRelationReqVo) {

    }

}