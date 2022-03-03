package com.deepcre.radar.service.impl;

import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.dao.ClientDao;
import com.deepcre.radar.entity.ClientEntity;
import com.deepcre.radar.service.ClientService;


@Service("clientService")
public class ClientServiceImpl extends ServiceImpl<ClientDao, ClientEntity> implements ClientService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String clientName = (String) params.get("clientName");
        String phone = (String) params.get("phone");
        String company = (String) params.get("company");
        QueryWrapper<ClientEntity> clientEntityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(clientName)){
            clientEntityQueryWrapper.like("client_name",clientName);
        }
        if (!StringUtils.isNullOrEmpty(phone)){
            clientEntityQueryWrapper.like("phone",phone);
        }
        if (!StringUtils.isNullOrEmpty(company)){
            clientEntityQueryWrapper.like("company",company);
        }
        IPage<ClientEntity> page = this.page(
                new Query<ClientEntity>().getPage(params),
                clientEntityQueryWrapper
        );

        return new PageUtils(page);
    }

}