package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.CategoryDao;
import com.deepcre.radar.dao.MaterialDao;
import com.deepcre.radar.entity.CategoryEntity;
import com.deepcre.radar.entity.MaterialEntity;
import com.deepcre.radar.entity.ProductEntity;
import com.deepcre.radar.vo.MatForSemiRespVo;
import com.deepcre.radar.vo.SemiAndMatListRespVo;
import com.deepcre.radar.vo.SemiRespVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.SemimanufacturesDao;
import com.deepcre.radar.entity.SemimanufacturesEntity;
import com.deepcre.radar.service.SemimanufacturesService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("semimanufacturesService")
public class SemimanufacturesServiceImpl extends ServiceImpl<SemimanufacturesDao, SemimanufacturesEntity> implements SemimanufacturesService {

    @Value("${path.img.semiPath}")
    private String IMG_PATH;

    @Value("${path.database.img.semi}")
    private String DATABASE_IMG_PATH;

    @Autowired
    MaterialDao materialDao;

    @Autowired
    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SemimanufacturesEntity> wrapper = new QueryWrapper<>();
        if (params.get("key")!=null || params.get("key")!=""){
            String key = (String) params.get("key");
            wrapper.like("semi_name",key).or().like("semi_model",key);
        }
        IPage<SemimanufacturesEntity> page = this.page(
                new Query<SemimanufacturesEntity>().getPage(params),wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<SemimanufacturesEntity> records = page.getRecords();
        List<SemiRespVo> collect = records.stream().map(semimanufacturesEntity -> {
            SemiRespVo semiRespVo = new SemiRespVo();
            BeanUtils.copyProperties(semimanufacturesEntity,semiRespVo);
            //设置类别名称
            CategoryEntity categoryEntity = categoryDao.selectById(semimanufacturesEntity.getCatId());
            semiRespVo.setCatName(categoryEntity.getCatName());
            return semiRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(collect);

        return pageUtils;
    }

    @Override
    public List queryByTree(Map<String, Object> params) {
        List<SemimanufacturesEntity> semimanufacturesEntities = this.baseMapper.selectList(null);
        //查询所有的原料
        List<MaterialEntity> materialEntities = materialDao.selectList(null);


        List<SemiAndMatListRespVo> semiList = semimanufacturesEntities.stream().map(semimanufacturesEntity -> {
            //处理原料数据
            List<MatForSemiRespVo> matList = materialEntities.stream().map(materialEntity -> {
                MatForSemiRespVo matForSemiRespVo = new MatForSemiRespVo();
                matForSemiRespVo.setId(materialEntity.getMatId());
                matForSemiRespVo.setNameAndModel(materialEntity.getMatName()+"-"+materialEntity.getMatModel());
                matForSemiRespVo.setParentId(semimanufacturesEntity.getSemiId());
                return matForSemiRespVo;
            }).collect(Collectors.toList());

            SemiAndMatListRespVo semiAndMatListRespVo = new SemiAndMatListRespVo();
            semiAndMatListRespVo.setId(semimanufacturesEntity.getSemiId());
            semiAndMatListRespVo.setNameAndModel(semimanufacturesEntity.getSemiName()+"-"+semimanufacturesEntity.getSemiModel());

            semiAndMatListRespVo.setChildren(matList);
            return semiAndMatListRespVo;
        }).collect(Collectors.toList());
        return semiList;
    }

    /*
    根据id获取半成品详细信息
     */
    @Override
    public SemiRespVo getDetailInfoById(Long semiId) {
        SemimanufacturesEntity semimanufacturesEntity = this.getById(semiId);
        SemiRespVo semiRespVo = new SemiRespVo();
        BeanUtils.copyProperties(semimanufacturesEntity,semiRespVo);
        semiRespVo.setCatName("半成品");
        return semiRespVo;
    }

    /**
     * 上传产品图片
     * @param multipartFile
     */
    @Override
    @Transactional
    public void uploadImg(MultipartFile multipartFile, Long semiId) throws IOException {
        multipartFile.transferTo(new File(IMG_PATH+multipartFile.getOriginalFilename()));

        //更新数据库内半成品图片地址
        String path = DATABASE_IMG_PATH+multipartFile.getOriginalFilename();
        SemimanufacturesEntity semimanufacturesEntity = new SemimanufacturesEntity();
        semimanufacturesEntity.setSemiId(semiId);
        semimanufacturesEntity.setImgUrl(path);
        this.updateById(semimanufacturesEntity);
    }

}