package com.deepcre.radar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.radar.entity.ProcurementEntity;
import com.deepcre.radar.vo.ProcurementExcelRespVo;
import com.deepcre.radar.vo.ProcurementInfoRespVo;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-09 14:26:10
 */
public interface ProcurementService extends IService<ProcurementEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int add(ProcurementEntity procurement);

    List<ProcurementExcelRespVo> exportAsExcel(Map<String, Object> params);

    List<ProcurementInfoRespVo> queryAll();
}

