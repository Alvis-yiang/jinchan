package com.deepcre.radar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.StorageDao;
import com.deepcre.radar.entity.StorageEntity;
import com.deepcre.radar.service.StorageService;


@Service("storageService")
public class StorageServiceImpl extends ServiceImpl<StorageDao, StorageEntity> implements StorageService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StorageEntity> page = this.page(
                new Query<StorageEntity>().getPage(params),
                new QueryWrapper<StorageEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查出所有库位以及子库位，以树形结构组装起来
     */
    @Override
    public List<StorageEntity> listWithTree() {
        //1.查出所有库位

        List<StorageEntity> storageEntities = baseMapper.selectList(null);

        //2.组装成父子的树形结构
        //2.1 找到所有的一级库位
        List<StorageEntity> level1Stores = storageEntities.stream().filter(storageEntity ->
                storageEntity.getParentId() == 0
        ).map((store)->{
            store.setChildren(getChildrens(store,storageEntities));
            return store;
        }).collect(Collectors.toList());


        return level1Stores;
    }

    //递归查找所有库位的子库位
    private List<StorageEntity> getChildrens(StorageEntity root,List<StorageEntity> all){
        List<StorageEntity> children = all.stream().filter(storageEntity -> {
            return storageEntity.getParentId() == root.getStorId();
        }).map(store -> {
            //找到子菜单
            store.setChildren(getChildrens(store,all));
            return store;
        }).collect(Collectors.toList());

        return children;

    }

    /**
     * 根据id删除库位
     * @param asList
     */
    @Override
    public void removeStorByIds(List<Long> asList) {
        //TODO 1.检查当前要删除的库位，是否被其他地方引用

        //逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

}