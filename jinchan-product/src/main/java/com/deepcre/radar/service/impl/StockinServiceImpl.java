package com.deepcre.radar.service.impl;

import com.deepcre.common.utils.Constant;
import com.deepcre.radar.dao.*;
import com.deepcre.radar.entity.*;
import com.deepcre.radar.service.*;
import com.deepcre.radar.vo.StockinReqVo;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import org.springframework.transaction.annotation.Transactional;

import static com.deepcre.common.utils.Constant.uuidmodel;


@Service("stockinService")
public class StockinServiceImpl extends ServiceImpl<StockinDao, StockinEntity> implements StockinService {

    @Autowired
    ProductDao productDao;

    @Autowired
    SemimanufacturesDao semimanufacturesDao;

    @Autowired
    MaterialDao materialDao;

    @Autowired
    SeriesDao seriesDao;

    @Autowired
    StorageService storageService;

    @Autowired
    ProductSalService productSalService;

    @Autowired
    IdentityService identityService;

    @Autowired
    KeepingService keepingService;

    @Autowired
    KeepingAmountDao keepingAmountDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockinEntity> page = this.page(
                new Query<StockinEntity>().getPage(params),
                new QueryWrapper<StockinEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *  确认入库品是否存在
     *  存在则返回入库品id，仓库列表
     * @param params
     * @return
     */
    @Override
    public Map isExit(Map<String, Object> params) {
        Map result = new HashMap();
        Long catId = Long.parseLong(params.get("catId").toString()) ;
        String name = (String) params.get("name");
        String model = (String) params.get("model");

        if (catId == 1){//产品
            ProductEntity productEntity = productDao.selectOne(new QueryWrapper<ProductEntity>().eq("product_name",name).eq("product_model",model));
            if (null != productEntity){
                result.put("isexit",true);
                result.put("id",productEntity.getProductId());
                return result;
            }
        }else if (catId == 2){//半成品
            SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectOne(new QueryWrapper<SemimanufacturesEntity>().eq("semi_name", name).eq("semi_model", model));
            if (null != semimanufacturesEntity){
                result.put("isexit",true);
                result.put("id",semimanufacturesEntity.getSemiId());
                return result;
            }
        }else if (catId == 3){//原料
            MaterialEntity materialEntity = materialDao.selectOne(new QueryWrapper<MaterialEntity>().eq("mat_name", name).eq("mat_model", model));
            if (null != materialEntity){
                result.put("isexit",true);
                result.put("id",materialEntity.getMatId());
                return result;
            }
        }
        result.put("isexit",false);
        return result;
    }

    /**
     * 入库
     * @param stockinReqVo
     */
    @Transactional
    @Override
    public List stockin(StockinReqVo stockinReqVo) {
        //返回产品编号
        List<String> productNos = new ArrayList<>();

        StockinEntity stockinEntity = new StockinEntity();
        BeanUtils.copyProperties(stockinReqVo,stockinEntity);
//        System.out.println("提交的数据："+stockinReqVo);
//        System.out.println(stockinReqVo.getCatId());
        //TODO 和操作员工进行绑定
        //虚拟设置员工id
        stockinEntity.setEmpid(1L);
        //1、将数据保存到入库表
        this.save(stockinEntity);
        //2、为入库品添加标识（根据入库的数量，一件入库品对应一个标识）
        for (int i = 0; i < stockinReqVo.getCount(); i++) {
            IdentityEntity identityEntity = new IdentityEntity();
            BeanUtils.copyProperties(stockinReqVo,identityEntity);//将提交的数据拷贝到标识实体
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            identityEntity.setIdentityId(uuid);//设置唯一标识

            // 3、若为产品，则添加产品编号,添加记录到产品销售属性表
            if (stockinReqVo.getCatId()==1){
                //3.1 创建产品编号
                Date date = new Date();
                String time = new SimpleDateFormat("yyyyMMdd").format(date);
                String str = uuid.substring(uuid.length()-6,uuid.length());
                if (str == uuidmodel){
                    String u = UUID.randomUUID().toString().replaceAll("-","");
                    str = u.substring(u.length()-6,u.length());
                }
                String productNo = new String("WZ"+str+time+stockinReqVo.getModel());
                identityEntity.setProductNo(productNo);
                productNos.add(productNo);//编号添加到返回列表中

                //3.2 创建产品销售属性实体
                ProductSalEntity productSalEntity = new ProductSalEntity();
                productSalEntity.setProductId(identityEntity.getGoodsId());//产品id
                productSalEntity.setIdentityId(uuid);//标识id
                productSalEntity.setProductNo(productNo);//产品编号
                productSalEntity.setPrice(stockinReqVo.getPrice());//价格

                productSalService.save(productSalEntity);

            }
            //将记录添加到标识表
            identityService.save(identityEntity);

            //4、添加记录到库存表，库存表内记录每件库存品的位置
            KeepingEntity keepingEntity = new KeepingEntity();
            BeanUtils.copyProperties(stockinReqVo,keepingEntity);
            keepingEntity.setIdentityId(uuid);

            keepingService.save(keepingEntity);
        }
        //5、更新库存量表
        //5.1 查询库存量表内是否存在该入库品
        QueryWrapper<KeepingAmountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cat_id",stockinReqVo.getCatId()).eq("goods_id",stockinReqVo.getGoodsId());
        KeepingAmountEntity keepingAmountEntity = keepingAmountDao.selectOne(queryWrapper);
        //5.2 存在则加上相应的数量，不存在则创建新记录
        if (null != keepingAmountEntity){
            Double totle = keepingAmountEntity.getAmount()+stockinReqVo.getCount();
            keepingAmountEntity.setAmount(totle);//设置新的库存量
            keepingAmountDao.update(keepingAmountEntity,queryWrapper);
        }else {
            KeepingAmountEntity keepingAmountEntity1 = new KeepingAmountEntity();
            keepingAmountEntity1.setCatId(stockinReqVo.getCatId());
            keepingAmountEntity1.setGoodsId(stockinReqVo.getGoodsId());
            keepingAmountEntity1.setAmount(stockinReqVo.getCount());
            keepingAmountDao.insert(keepingAmountEntity1);
        }
        //返回产品编号
        return productNos;
    }

}