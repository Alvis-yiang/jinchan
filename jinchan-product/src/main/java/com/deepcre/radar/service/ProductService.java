package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProductEntity;
import com.deepcre.radar.vo.ProductRespVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 产品信息表：一个id代表一种产品，由名称和型号共同决定。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface ProductService extends IService<ProductEntity> {

    PageUtils queryPage(Map<String, Object> params);

    ProductRespVo getDetailInfoById(Long productId);

    void uploadImg(MultipartFile multipartFile,Long productId) throws IOException;
}

