package com.deepcre.radar.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepcre.common.utils.ExportExcel;
import com.deepcre.radar.dao.MaterialDao;
import com.deepcre.radar.entity.KeepingAmountEntity;
import com.deepcre.radar.entity.MaterialEntity;
import com.deepcre.radar.vo.KeepingRespVo;
import com.deepcre.radar.vo.ProcurementExcelRespVo;
import com.deepcre.radar.vo.ProcurementInfoRespVo;
import com.deepcre.radar.vo.ProductRelationRespVo;
import com.mysql.cj.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProcurementDao;
import com.deepcre.radar.entity.ProcurementEntity;
import com.deepcre.radar.service.ProcurementService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;


@Service("procurementService")
public class ProcurementServiceImpl extends ServiceImpl<ProcurementDao, ProcurementEntity> implements ProcurementService {

    @Autowired
    MaterialDao materialDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ProcurementEntity> queryWrapper = new QueryWrapper<ProcurementEntity>();
        //查询matid的过滤条件
        QueryWrapper<MaterialEntity> materialEntityQueryWrapper = new QueryWrapper<MaterialEntity>();
        String name = (String) params.get("name");
        String model = (String) params.get("model");
        if (!StringUtils.isNullOrEmpty(name)){
            materialEntityQueryWrapper.like("mat_name",name);
        }
        if (!StringUtils.isNullOrEmpty(model)){
            materialEntityQueryWrapper.like("mat_model",model);
        }
        if (null != materialEntityQueryWrapper){
            List<MaterialEntity> materialEntities = materialDao.selectList(materialEntityQueryWrapper);
            //没有该原料信息就返回空
            if(null == materialEntities){
                List<ProcurementInfoRespVo> respVos = new ArrayList<>();
                PageUtils pageUtils = new PageUtils(new Page<ProcurementEntity>() {
                });
                pageUtils.setList(respVos);
                return pageUtils;
            }

            materialEntities.stream().map(materialEntity -> {
                queryWrapper.or().eq("goods_id",materialEntity.getMatId());
                return materialEntity.getMatId();
            }).collect(Collectors.toList());


        }

        IPage<ProcurementEntity> page = this.page(
                new Query<ProcurementEntity>().getPage(params),queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<ProcurementEntity> records = page.getRecords();
        //没有记录就无需再查了
        if (records.isEmpty()){
            List<ProcurementInfoRespVo> respVos = new ArrayList<>();
            pageUtils.setList(respVos);
            return pageUtils;
        }

            List<ProcurementInfoRespVo> list = records.stream().map(procurementEntity -> {
                ProcurementInfoRespVo procurementInfoRespVo = new ProcurementInfoRespVo();
                BeanUtils.copyProperties(procurementEntity, procurementInfoRespVo);

                //到原料表查询原料信息：根据原料的id
                MaterialEntity materialEntity = materialDao.selectById(procurementEntity.getGoodsId());
                //将原料信息设置到model
                procurementInfoRespVo.setMatName(materialEntity.getMatName());
                procurementInfoRespVo.setMatModel(materialEntity.getMatModel());
                procurementInfoRespVo.setMatUnit(materialEntity.getMatUnit());
                procurementInfoRespVo.setMatDescription(materialEntity.getMatDescription());

                return procurementInfoRespVo;
            }).collect(Collectors.toList());

            pageUtils.setList(list);


            return pageUtils;

    }

    //添加新的采购需求，若还未购买，添加同一原料采购需求时，修改数量，不新增记录
    @Override
    @Transactional
    public int add(ProcurementEntity procurement) {
        ProcurementEntity p = this.baseMapper.selectOne(new QueryWrapper<ProcurementEntity>().eq("goods_id", procurement.getGoodsId()));
        int i;
        if (null != p){
            procurement.setProcurementId(p.getProcurementId());
            procurement.setNum(p.getNum()+procurement.getNum());
            i = this.baseMapper.updateById(procurement);
        }else {
            i = this.baseMapper.insert(procurement);
        }
//        System.out.println("插入：" + i);
        return i;
    }

//    请求导出为excel
    @Override
    public List<ProcurementExcelRespVo> exportAsExcel(Map<String, Object> params) {
        QueryWrapper<ProcurementEntity> queryWrapper = new QueryWrapper<>();
//        List<Long> ids = (List<Long>) params.get("ids");
//        for (int i = 0; i < ids.size(); i++) {
//            queryWrapper.or().eq("procurement_id",ids.get(i));
//        }
        Long id = Long.valueOf((String) params.get("ids")) ;
        queryWrapper.eq("procurement_id",id);

        IPage<ProcurementEntity> page = this.page(
                new Query<ProcurementEntity>().getPage(params),queryWrapper
        );
        List<ProcurementEntity> records = page.getRecords();

        //最终处理好需要导出为excel的数据
        List<ProcurementExcelRespVo> list = records.stream().map(procurementEntity -> {
            ProcurementExcelRespVo procurementExcelRespVo = new ProcurementExcelRespVo();
            BeanUtils.copyProperties(procurementEntity, procurementExcelRespVo);
            return procurementExcelRespVo;

        }).collect(Collectors.toList());

        return list;
    }

    //请求所有数据，用于excel导出
    @Override
    public List<ProcurementInfoRespVo> queryAll() {
        List<ProcurementEntity> procurementEntityList = this.baseMapper.selectList(null);
        List<ProcurementInfoRespVo> list = procurementEntityList.stream().map(procurementEntity -> {
            ProcurementInfoRespVo procurementInfoRespVo = new ProcurementInfoRespVo();
            BeanUtils.copyProperties(procurementEntity, procurementInfoRespVo);

            //到原料表查询原料信息：根据原料的id
            MaterialEntity materialEntity = materialDao.selectById(procurementEntity.getGoodsId());
            //将原料信息设置到model
            procurementInfoRespVo.setMatName(materialEntity.getMatName());
            procurementInfoRespVo.setMatModel(materialEntity.getMatModel());
            procurementInfoRespVo.setMatUnit(materialEntity.getMatUnit());
            procurementInfoRespVo.setMatDescription(materialEntity.getMatDescription());

            return procurementInfoRespVo;
        }).collect(Collectors.toList());
        return list;
    }

}