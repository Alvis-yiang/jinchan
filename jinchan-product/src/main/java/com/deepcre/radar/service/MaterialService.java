package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.MaterialEntity;
import com.deepcre.radar.vo.MatRespVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 原料信息表：一个id代表一种原料，由名称和型号共同决定。
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-07 14:11:16
 */
public interface MaterialService extends IService<MaterialEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MatRespVo getDetailInfoById(Long matId);

    void uploadImg(MultipartFile multipartFile, Long matId) throws IOException;
}

