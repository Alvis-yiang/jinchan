package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.SemimanufacturesEntity;
import com.deepcre.radar.vo.SemiRespVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 半成品信息表：一个id代表一种半成品，由名称和型号共同决定。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface SemimanufacturesService extends IService<SemimanufacturesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List queryByTree(Map<String,Object> params);

    SemiRespVo getDetailInfoById(Long semiId);

    void uploadImg(MultipartFile multipartFile, Long semiId) throws IOException;
}

