package com.deepcre.radar.jinchanemployee.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.jinchanemployee.dao.SysUserDao;
import com.deepcre.radar.jinchanemployee.entity.SysUserEntity;
import com.deepcre.radar.jinchanemployee.service.SysUserService;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public SysUserEntity getUserInfoById(Long empId) {
        SysUserEntity sysUserEntity = this.baseMapper.selectById(empId);
        return sysUserEntity;
    }

}