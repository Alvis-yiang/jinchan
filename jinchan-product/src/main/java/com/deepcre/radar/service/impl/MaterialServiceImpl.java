package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.CategoryDao;
import com.deepcre.radar.entity.CategoryEntity;
import com.deepcre.radar.entity.SemimanufacturesEntity;
import com.deepcre.radar.vo.MatRespVo;
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

import com.deepcre.radar.dao.MaterialDao;
import com.deepcre.radar.entity.MaterialEntity;
import com.deepcre.radar.service.MaterialService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("materialService")
public class MaterialServiceImpl extends ServiceImpl<MaterialDao, MaterialEntity> implements MaterialService {

    @Value("${path.img.matPath}")
    private String IMG_PATH;

    @Value("${path.database.img.mat}")
    private String DATABASE_IMG_PATH;

    @Autowired
    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MaterialEntity> wrapper = new QueryWrapper<>();
        if (params.get("key")!=null || params.get("key")!=""){
            String key = (String) params.get("key");
            wrapper.like("mat_name",key).or().like("mat_model",key);
        }
        IPage<MaterialEntity> page = this.page(
                new Query<MaterialEntity>().getPage(params),wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<MaterialEntity> records = page.getRecords();
        List<MatRespVo> collect = records.stream().map(materialEntity -> {
            MatRespVo matRespVo = new MatRespVo();
            BeanUtils.copyProperties(materialEntity,matRespVo);
            //设置类别名称
            CategoryEntity categoryEntity = categoryDao.selectById(materialEntity.getCatId());
            matRespVo.setCatName(categoryEntity.getCatName());
            return matRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(collect);
        return pageUtils;
    }

    @Override
    public MatRespVo getDetailInfoById(Long matId) {
        MaterialEntity materialEntity = this.getById(matId);
        MatRespVo matRespVo = new MatRespVo();
        BeanUtils.copyProperties(materialEntity,matRespVo);
        matRespVo.setCatName("原料");
        return matRespVo;
    }

    /**
     * 上传产品图片
     * @param multipartFile
     */
    @Override
    @Transactional
    public void uploadImg(MultipartFile multipartFile, Long matId) throws IOException {
        multipartFile.transferTo(new File(IMG_PATH+multipartFile.getOriginalFilename()));

        //更新数据库内半成品图片地址
        String path = DATABASE_IMG_PATH+multipartFile.getOriginalFilename();
        MaterialEntity materialEntity = new MaterialEntity();
        materialEntity.setMatId(matId);
        materialEntity.setImgUrl(path);
        this.updateById(materialEntity);
    }

}