package com.deepcre.radar.utils;

import com.deepcre.radar.dao.StorageDao;
import com.deepcre.radar.entity.StorageEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @Author: 如果这段代码能正常工作，请记住作者是：alvis-yiang
 * 如果不能正常工作，那我也不知道是谁写的
 * @Date: Created in 11:10 2022/2/22
 * @Description:
 */
@Data
public class StorUtils{

    @Autowired
    StorageDao storageDao;
    /**
     * 仓库名称
     */
    private String storName;

    public StorUtils(){}

    /**
     * 获取仓库名称
     */
    public String getStorName(Long storId){
        String storname = "";
        System.out.println("要查询的仓库"+storId);
        StorageEntity storageEntity = storageDao.selectById(storId);
        while (storageEntity.getParentId() != 0) {
            storname = storageEntity.getStorName() + storname;
            storageEntity = storageDao.selectById(storageEntity.getParentId());
        }
        storname = storageEntity.getStorName() + storname;
        return storname;
    }


}
