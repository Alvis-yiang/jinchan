package com.deepcre.radar.service.impl;

import com.deepcre.radar.dao.CategoryDao;
import com.deepcre.radar.dao.SeriesDao;
import com.deepcre.radar.entity.CategoryEntity;
import com.deepcre.radar.entity.SeriesEntity;
import com.deepcre.radar.vo.ProductRespVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ProductDao;
import com.deepcre.radar.entity.ProductEntity;
import com.deepcre.radar.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Value("${path.img.productPath}")
    private String IMG_PATH;

    @Value("${path.database.img.product}")
    private String DATABASE_IMG_PATH;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    SeriesDao seriesDao;

    @Transactional
    @Override
    public PageUtils queryPage(Map<String, Object> params){
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        if (params.get("key")!=null || params.get("key")!=""){
            String key = (String) params.get("key");
            wrapper.like("product_name",key).or().like("product_model",key);
        }
        IPage<ProductEntity> page = this.page(
                new Query<ProductEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<ProductEntity> records = page.getRecords();
        List<ProductRespVo> respVos = records.stream().map((productEntity)->{
            ProductRespVo productRespVo = new ProductRespVo();
            BeanUtils.copyProperties(productEntity,productRespVo);
            //设置系列名称和类别名称
            CategoryEntity categoryEntity = categoryDao.selectById(productEntity.getCatId());
            SeriesEntity seriesEntity = seriesDao.selectById(productEntity.getSeriesId());
            productRespVo.setCatName(categoryEntity.getCatName());
            productRespVo.setSeriesName(seriesEntity.getSeriesName());

            return productRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    /**
     * 根据产品id获取产品详细信息（同selectpage）
     * @param productId
     * @return
     */
    @Override
    public ProductRespVo getDetailInfoById(Long productId) {
        ProductEntity productEntity = this.getById(productId);
        ProductRespVo productRespVo = new ProductRespVo();
        BeanUtils.copyProperties(productEntity,productRespVo);
        //设置系列名称和类别名称
        CategoryEntity categoryEntity = categoryDao.selectById(productEntity.getCatId());
        SeriesEntity seriesEntity = seriesDao.selectById(productEntity.getSeriesId());
        productRespVo.setCatName(categoryEntity.getCatName());
        productRespVo.setSeriesName(seriesEntity.getSeriesName());
        return productRespVo;
    }

    /**
     * 上传产品图片
     * @param multipartFile
     */
    @Override
    @Transactional
    public void uploadImg(MultipartFile multipartFile,Long productId) throws IOException {
        multipartFile.transferTo(new File(IMG_PATH+multipartFile.getOriginalFilename()));

        //更新数据库内产品图片地址
        String path = DATABASE_IMG_PATH+multipartFile.getOriginalFilename();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(productId);
        productEntity.setImgUrl(path);
        this.updateById(productEntity);

    }

}