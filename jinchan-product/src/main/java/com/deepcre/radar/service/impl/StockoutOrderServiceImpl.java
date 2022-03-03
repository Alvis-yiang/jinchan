package com.deepcre.radar.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deepcre.common.utils.R;
import com.deepcre.radar.dao.*;
import com.deepcre.radar.entity.*;
import com.deepcre.radar.feign.EmployeeFeignService;
import com.deepcre.radar.feign.RenrenFastFeignService;
import com.deepcre.radar.feign.entity.SysUserEntity;
import com.deepcre.radar.utils.PDFUtils;
import com.deepcre.radar.utils.StorUtils;
import com.deepcre.radar.vo.AgreeStockoutDetailInfoRespVo;
import com.deepcre.radar.vo.AgreeStockoutGoodsAndStorInfoRespVo;
import com.deepcre.radar.vo.ApplyStockoutRespVo;
import com.deepcre.radar.vo.StockoutOrderDetailRespVo;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.Query;

import com.deepcre.radar.service.StockoutOrderService;
import org.springframework.transaction.annotation.Transactional;

import static com.deepcre.common.utils.Constant.uuidmodel;


@Service("stockoutOrderService")
public class StockoutOrderServiceImpl extends ServiceImpl<StockoutOrderDao, StockoutOrderEntity> implements StockoutOrderService {

    @Autowired
    StockoutDao stockoutDao;

    @Autowired
    StockoutOrderDetailDao stockoutOrderDetailDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    SemimanufacturesDao semimanufacturesDao;

    @Autowired
    MaterialDao materialDao;

    @Autowired
    KeepingDao keepingDao;

    @Autowired
    KeepingAmountDao keepingAmountDao;

    @Autowired
    StorageDao storageDao;

    @Autowired
    StorKeepingAmountDao storKeepingAmountDao;

    @Autowired
    StockoutOrderPrintDao stockoutOrderPrintDao;

    @Autowired
    EmployeeFeignService employeeFeignService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<StockoutOrderEntity> stockoutOrderEntityQueryWrapper = new QueryWrapper<>();
        stockoutOrderEntityQueryWrapper.eq("audit_result",0);
        IPage<StockoutOrderEntity> page = this.page(
                new Query<StockoutOrderEntity>().getPage(params),stockoutOrderEntityQueryWrapper

        );
        List<StockoutOrderEntity> records = page.getRecords();
        //没有记录直接返回空的page
        if (records.isEmpty()){
            return new PageUtils(page);
        }
        //有记录，设置返回的出库单详情
        //每一个出库单对应多条出库记录，封装成list
        List<StockoutOrderDetailRespVo> result = records.stream().map(stockoutOrderEntity -> {
            StockoutOrderDetailRespVo stockoutOrderDetailRespVo = new StockoutOrderDetailRespVo();
            BeanUtils.copyProperties(stockoutOrderEntity,stockoutOrderDetailRespVo);
            //根据出库单id，查询该id下所有的出库id
            List<StockoutOrderDetailEntity> stockoutOrderDetailEntities = stockoutOrderDetailDao.selectList(new QueryWrapper<StockoutOrderDetailEntity>()
                    .eq("order_id", stockoutOrderEntity.getOrderId()));
//            System.out.println("获取到的entity："+stockoutOrderDetailEntities);
//            List<Long> stockoutIds =null;
//            for (int i= 0; i < stockoutOrderDetailEntities.size(); i++) {
//                stockoutIds.add(stockoutOrderDetailEntities.get(i).getStockoutId());
//            }
//            for (StockoutOrderDetailEntity entity:
//                 stockoutOrderDetailEntities) {
//                stockoutIds.add(entity.getStockoutId());
//            }

            //根据出库id，到出库表查询所有记录
            QueryWrapper<StockoutEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.and(wrapper ->{
                for (int i = 0; i < stockoutOrderDetailEntities.size(); i++) {
                    wrapper.or().eq("out_id",stockoutOrderDetailEntities.get(i).getStockoutId());
                }
            });

            List<StockoutEntity> list = stockoutDao.selectList(queryWrapper).stream().collect(Collectors.toList());
            //设置申请人
            Long empid = list.get(0).getEmpid();//申请出库员工id
            String userName = employeeFeignService.getInfoById(empid);
//            R infoById = renrenFastFeignService.getInfoById(empid);
//            System.out.println("调用系统服务："+infoById);
//            SysUserEntity user = (SysUserEntity) info.get("user");

            stockoutOrderDetailRespVo.setApplyPerson(userName);

            //将每一条出库记录数据进行处理，添加上前端展示所需其他属性
            List<ApplyStockoutRespVo> collect = list.stream().map(stockoutEntity -> {
                ApplyStockoutRespVo applyStockoutRespVo = new ApplyStockoutRespVo();
                BeanUtils.copyProperties(stockoutEntity, applyStockoutRespVo);
                if (stockoutEntity.getCatId() == 1) {//产品
                    ProductEntity productEntity = productDao.selectById(stockoutEntity.getGoodsId());
                    applyStockoutRespVo.setCatName("成品");
                    applyStockoutRespVo.setGoodsName(productEntity.getProductName());
                    applyStockoutRespVo.setUnit(productEntity.getProductUnit());
                    applyStockoutRespVo.setModel(productEntity.getProductModel());
                    applyStockoutRespVo.setDescription(productEntity.getProductDescription());
                } else if (stockoutEntity.getCatId() == 2) {//半成品
                    SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(stockoutEntity.getGoodsId());
                    applyStockoutRespVo.setCatName("半成品");
                    applyStockoutRespVo.setGoodsName(semimanufacturesEntity.getSemiName());
                    applyStockoutRespVo.setUnit(semimanufacturesEntity.getSemiUnit());
                    applyStockoutRespVo.setModel(semimanufacturesEntity.getSemiModel());
                    applyStockoutRespVo.setDescription(semimanufacturesEntity.getSemiDescription());
                } else {//原料
                    MaterialEntity materialEntity = materialDao.selectById(stockoutEntity.getGoodsId());
                    applyStockoutRespVo.setCatName("原料");
                    applyStockoutRespVo.setGoodsName(materialEntity.getMatName());
                    applyStockoutRespVo.setUnit(materialEntity.getMatUnit());
                    applyStockoutRespVo.setModel(materialEntity.getMatModel());
                    applyStockoutRespVo.setDescription(materialEntity.getMatDescription());
                }
                return applyStockoutRespVo;
            }).collect(Collectors.toList());

            stockoutOrderDetailRespVo.setList(collect);
            return stockoutOrderDetailRespVo;
        }).collect(Collectors.toList());
        //将数据封装成page对象
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(result);
        return pageUtils;
    }

    /**
     * 新增出库单
     * @param stockoutIds
     * @param empId
     * @return
     */
    @Override
    @Transactional
    public String createOrder(Long[] stockoutIds, Long empId) {
        //出库单id：wz+8位日期+6位uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String str = uuid.substring(uuid.length()-6,uuid.length());
        if (str == uuidmodel){
            String u = UUID.randomUUID().toString().replaceAll("-","");
            str = u.substring(u.length()-6,u.length());
        }
        Date date = new Date();
        String time = new SimpleDateFormat("yyyyMMdd").format(date);
        String orderId = new String("WZ"+time+str);

        //将一条记录添加到出库单表
        StockoutOrderEntity stockoutOrderEntity = new StockoutOrderEntity();
        stockoutOrderEntity.setOrderId(orderId);
        stockoutOrderEntity.setCreateDate(date);
        stockoutOrderEntity.setCreateEmpid(empId);
        stockoutOrderEntity.setAuditResult(0);
        this.save(stockoutOrderEntity);

        //将出库表状态修改为 2：出库单已生成
        for (Long stockId:stockoutIds) {
            StockoutEntity stockoutEntity = new StockoutEntity();
            stockoutEntity.setOutId(stockId);
            stockoutEntity.setState(2);
            stockoutDao.updateById(stockoutEntity);

            //将数据添加到出库单详情表,绑定出库单id和出库id
            StockoutOrderDetailEntity stockoutOrderDetailEntity = new StockoutOrderDetailEntity();
            stockoutOrderDetailEntity.setOrderId(orderId);//设置出库单id
            stockoutOrderDetailEntity.setStockoutId(stockId);//设置出库id
            stockoutOrderDetailDao.insert(stockoutOrderDetailEntity);
        }
        return orderId;
    }

    /**
     * 出库审核
     * @param stockoutOrder
     */
    @Override
    @Transactional
    public void audit(StockoutOrderEntity stockoutOrder) {
        System.out.println("接收到的出库信息"+stockoutOrder);
        Integer auditResult = stockoutOrder.getAuditResult();
        //1.同意出库
        if (auditResult == 1){
            //1.1 更新出库单表信息
            this.updateById(stockoutOrder);
            StockoutOrderEntity updatedOrder = this.getById(stockoutOrder.getOrderId());
            StockoutOrderEntity stockoutOrderEntity = this.baseMapper.selectById(stockoutOrder.getOrderId());
            //1.2 从仓库调取出库品，更新库存表
            List<StockoutOrderDetailEntity> stockoutOrderDetailEntityList = stockoutOrderDetailDao.selectList(new QueryWrapper<StockoutOrderDetailEntity>().eq("order_id", stockoutOrderEntity.getOrderId()));
            List<Long> ids = stockoutOrderDetailEntityList.stream().map(stockoutOrderDetailEntity -> {
                return stockoutOrderDetailEntity.getStockoutId();
            }).collect(Collectors.toList());

            for (Long id:ids) {
                //根据出库id，将出库表状态修改为 3：同意领取
                StockoutEntity stockout = new StockoutEntity();
                stockout.setOutId(id);
                stockout.setState(3);
                stockoutDao.updateById(stockout);

                StockoutEntity stockoutEntity = stockoutDao.selectById(id);
                //到仓库产品库存量表内查询每个仓库含该库存品的数量，按照数量的降序排列
                List<StorKeepingAmountEntity> storKeepingAmountEntitys = storKeepingAmountDao.selectList(new QueryWrapper<StorKeepingAmountEntity>()
                        .eq("goods_catid", stockoutEntity.getCatId())
                        .eq("goods_id", stockoutEntity.getGoodsId()).orderByDesc("amount"));

                //-------------------start----------------------------------------------------
                //生成打印单pdf版到服务器
                AgreeStockoutDetailInfoRespVo data = new AgreeStockoutDetailInfoRespVo();//用于打印的对象
                List<AgreeStockoutGoodsAndStorInfoRespVo> list = new ArrayList(); //该打印表内的list
                data.setOrderId(updatedOrder.getOrderId());//出库单号
                String userName = employeeFeignService.getInfoById(updatedOrder.getCreateEmpid());
                String auditName = employeeFeignService.getInfoById(updatedOrder.getAuditEmpid());
                data.setApplyEmpName(userName);//申请人姓名
                data.setCreateDate(updatedOrder.getCreateDate());//申请时间
                data.setAuditEmpName(auditName);//审核人姓名
                data.setAuditDate(updatedOrder.getAuditDate());//审核时间

                //--------------------------end----------------------------------------------------

                Integer requreAmount = stockoutEntity.getCount();//出库所需数量
                for (StorKeepingAmountEntity entity:storKeepingAmountEntitys) {
                    Integer amount = entity.getAmount();//该仓库库存量
                    Long storId = entity.getStorId();//该仓库id

                    //-------------------------start---------------------------------------
                    //出库单list数据
                    AgreeStockoutGoodsAndStorInfoRespVo respVo = new AgreeStockoutGoodsAndStorInfoRespVo();
                    String storName = "";//仓库名称
                    StorageEntity storageEntity = storageDao.selectById(storId);
                    while (storageEntity.getParentId() != 0) {
                        storName = storageEntity.getStorName() + storName;
                        storageEntity = storageDao.selectById(storageEntity.getParentId());
                    }
                    storName = storageEntity.getStorName() + storName;


                    Long goodsCatid = entity.getGoodsCatid();
                    //查询出库品的类型、名称、型号、单位
                    String catName = new String();
                    String goodsName = new String();
                    String model = new String();
                    String unit = new String();
                    if (goodsCatid == 1) {//产品
                        ProductEntity productEntity = productDao.selectById(entity.getGoodsId());
                        catName = "成品";
                        goodsName = productEntity.getProductName();
                        model = productEntity.getProductModel();
                        unit = productEntity.getProductUnit();
                    } else if (goodsCatid == 2) {//半成品
                        SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(entity.getGoodsId());
                        catName = "半成品";
                        goodsName = semimanufacturesEntity.getSemiName();
                        model = semimanufacturesEntity.getSemiModel();
                        unit = semimanufacturesEntity.getSemiUnit();
                    } else if (goodsCatid == 3) {//原料
                        MaterialEntity materialEntity = materialDao.selectById(entity.getGoodsId());
                        catName = "原料";
                        goodsName = materialEntity.getMatName();
                        model = materialEntity.getMatModel();
                        unit = materialEntity.getMatUnit();
                    }
                    respVo.setGoodsCatName(catName);
                    respVo.setGoodsName(goodsName);
                    respVo.setGoodsModel(model);
                    respVo.setUnit(unit);
                    respVo.setStorName(storName);

                    //--------------------------end----------------------------------------------------


                    //该仓库内该库存品的数量不足够应付该出库单中的这个出库请求
                    if (amount < requreAmount){
                        //将该仓库内的该库存品全部出库
                        QueryWrapper<KeepingEntity> keepingEntityQueryWrapper = new QueryWrapper<>();
                        keepingEntityQueryWrapper.eq("stor_id",storId)
                                .eq("cat_id",entity.getGoodsCatid())
                                .eq("goods_id",entity.getGoodsId());
                        KeepingEntity keepingEntity = new KeepingEntity();
                        keepingEntity.setIsAvailable(2);
                        //tbl_stor_keeping_amount表格内该库存品数量修改为0
                        StorKeepingAmountEntity storKeepingAmountEntity = new StorKeepingAmountEntity();
                        storKeepingAmountEntity.setAmount(0);
                        QueryWrapper<StorKeepingAmountEntity> eq = new QueryWrapper<StorKeepingAmountEntity>()
                                .eq("stor_id", storId)
                                .eq("goods_catid", entity.getGoodsCatid())
                                .eq("goods_id", entity.getGoodsId());
                        storKeepingAmountDao.update(storKeepingAmountEntity,eq);
                        //更新库存表内该库存品状态为2：同意出库
                        keepingDao.update(keepingEntity,keepingEntityQueryWrapper);
                        //添加记录到出库单打印表
                        StockoutOrderPrintEntity stockoutOrderPrintEntity = new StockoutOrderPrintEntity();
                        stockoutOrderPrintEntity.setStockoutOrderId(stockoutOrderEntity.getOrderId());
                        stockoutOrderPrintEntity.setApplyempId(stockoutOrderEntity.getCreateEmpid());
                        stockoutOrderPrintEntity.setApplyDate(stockoutOrderEntity.getCreateDate());
                        stockoutOrderPrintEntity.setAuditempId(stockoutOrderEntity.getAuditEmpid());
                        stockoutOrderPrintEntity.setAuditDate(stockoutOrderEntity.getAuditDate());
                        stockoutOrderPrintEntity.setAuditResult(stockoutOrderEntity.getAuditResult());
                        stockoutOrderPrintEntity.setGoodsCatid(entity.getGoodsCatid());
                        stockoutOrderPrintEntity.setGoodsId(entity.getGoodsId());
                        stockoutOrderPrintEntity.setStorId(storId);
                        stockoutOrderPrintEntity.setAmount(amount);
                        stockoutOrderPrintDao.insert(stockoutOrderPrintEntity);

                        //设置生成的出库打印单中该仓库出库的该库存品的数量
                        respVo.setAmount(amount);
                        list.add(respVo);

                    }else{
                        //从该仓库内选取所需该库存品的数量出库
                        QueryWrapper<KeepingEntity> keepingEntityQueryWrapper = new QueryWrapper<>();
                        keepingEntityQueryWrapper.eq("stor_id",storId)
                                .eq("cat_id",entity.getGoodsCatid())
                                .eq("goods_id",entity.getGoodsId());
                        KeepingEntity keepingEntity = new KeepingEntity();
                        keepingEntity.setIsAvailable(2);
                        List<KeepingEntity> keepingEntities = keepingDao.selectList(keepingEntityQueryWrapper);
                        //更新库存表内该库存品状态为2：同意出库
                        for (int i = 0; i < requreAmount; i++) {
                            keepingEntity.setKeepId(keepingEntities.get(i).getKeepId());
                            keepingDao.updateById(keepingEntity);
                        }
                        //tbl_stor_keeping_amount表格内该库存品数量减去出库量
                        StorKeepingAmountEntity storKeepingAmountEntity = new StorKeepingAmountEntity();
                        storKeepingAmountEntity.setAmount(amount-requreAmount);
                        QueryWrapper<StorKeepingAmountEntity> eq = new QueryWrapper<StorKeepingAmountEntity>()
                                .eq("stor_id", storId)
                                .eq("goods_catid", entity.getGoodsCatid())
                                .eq("goods_id", entity.getGoodsId());
                        storKeepingAmountDao.update(storKeepingAmountEntity,eq);
                        //添加记录到出库单打印表
                        StockoutOrderPrintEntity stockoutOrderPrintEntity = new StockoutOrderPrintEntity();
                        stockoutOrderPrintEntity.setStockoutOrderId(stockoutOrderEntity.getOrderId());
                        stockoutOrderPrintEntity.setApplyempId(stockoutOrderEntity.getCreateEmpid());
                        stockoutOrderPrintEntity.setApplyDate(stockoutOrderEntity.getCreateDate());
                        stockoutOrderPrintEntity.setAuditempId(stockoutOrderEntity.getAuditEmpid());
                        stockoutOrderPrintEntity.setAuditDate(stockoutOrderEntity.getAuditDate());
                        stockoutOrderPrintEntity.setAuditResult(stockoutOrderEntity.getAuditResult());
                        stockoutOrderPrintEntity.setGoodsCatid(entity.getGoodsCatid());
                        stockoutOrderPrintEntity.setGoodsId(entity.getGoodsId());
                        stockoutOrderPrintEntity.setStorId(storId);
                        stockoutOrderPrintEntity.setAmount(requreAmount);
                        stockoutOrderPrintDao.insert(stockoutOrderPrintEntity);

                        //设置生成的出库打印单中该仓库出库的该库存品的数量
                        respVo.setAmount(requreAmount);
                        list.add(respVo);

                        break;
                    }
                    requreAmount = requreAmount-amount;
                }
                data.setList(list);//最终处理完成的一条打印单信息
                //调用pdf生成方法进行生成并保存地址
                PDFUtils pdfUtils = new PDFUtils();
                pdfUtils.createStokoutOrderPDF(data);//pdf文件
                StockoutOrderEntity order = new StockoutOrderEntity();
                order.setOrderId(data.getOrderId());
                order.setFilePath("/radar/static/pdf-report/jinchan-radar/"+data.getOrderId()+".pdf");
                this.updateById(order);

            }
        }
        //2.不同意出库
        else if (auditResult == 2){
            //2.1 更新出库单表信息
            this.updateById(stockoutOrder);
            //2.2 更新出库表信息，修改状态为5（不同意）
            List<StockoutOrderDetailEntity> stockoutOrderDetailEntityList = stockoutOrderDetailDao.selectList(new QueryWrapper<StockoutOrderDetailEntity>().eq("order_id", stockoutOrder.getOrderId()));
            List<Long> ids = stockoutOrderDetailEntityList.stream().map(stockoutOrderDetailEntity -> {
                return stockoutOrderDetailEntity.getStockoutId();
            }).collect(Collectors.toList());
            for (Long id:ids) {
                StockoutEntity stockoutEntity = new StockoutEntity();
                stockoutEntity.setOutId(id);
                stockoutEntity.setState(5);
                stockoutDao.updateById(stockoutEntity);
                //2.3 将该出库单下的出库品的库存量修改回原来数量
                StockoutEntity outAfter = stockoutDao.selectOne(new QueryWrapper<StockoutEntity>().eq("out_id", id));
                QueryWrapper<KeepingAmountEntity> ke = new QueryWrapper<KeepingAmountEntity>().eq("cat_id", outAfter.getCatId())
                        .eq("goods_id", outAfter.getGoodsId());
                KeepingAmountEntity keepingAmountEntity = keepingAmountDao.selectOne(ke);
                keepingAmountEntity.setAmount(keepingAmountEntity.getAmount()+outAfter.getCount());
                keepingAmountDao.update(keepingAmountEntity,ke);
            }

        }
    }

    /**
     * 出库单打印
     * @param params
     * @return
     */
    @Override
    public PageUtils printStockoutOrder(Map<String, Object> params) {
        StorUtils storUtils = new StorUtils();

        QueryWrapper<StockoutOrderEntity> stockoutOrderEntityQueryWrapper = new QueryWrapper<>();
        stockoutOrderEntityQueryWrapper.eq("audit_result",1);//0:待审核;1:同意;2:驳回;3:已出库;
        IPage<StockoutOrderEntity> page = this.page(
                new Query<StockoutOrderEntity>().getPage(params),stockoutOrderEntityQueryWrapper
        );
        List<StockoutOrderEntity> records = page.getRecords();
        //没有记录直接返回空的page
        if (records.isEmpty()){
            return new PageUtils(page);
        }
        //有记录
        //每一张出库单对应多个库存品，
        List<AgreeStockoutDetailInfoRespVo> list = records.stream().map(stockoutOrderEntity -> {
            //返回的vo
            AgreeStockoutDetailInfoRespVo agreeStockoutDetailInfoRespVo = new AgreeStockoutDetailInfoRespVo();
            BeanUtils.copyProperties(stockoutOrderEntity, agreeStockoutDetailInfoRespVo);
            String userName = employeeFeignService.getInfoById(stockoutOrderEntity.getCreateEmpid());
            agreeStockoutDetailInfoRespVo.setApplyEmpName(userName);//申请人姓名
            String auditName = employeeFeignService.getInfoById(stockoutOrderEntity.getAuditEmpid());
            agreeStockoutDetailInfoRespVo.setAuditEmpName(auditName);//审核人姓名
            //每个库存品可能对应多个仓库
            ArrayList<AgreeStockoutGoodsAndStorInfoRespVo> agreeStockoutGoodsAndStorInfoRespVos = new ArrayList<>();

            List<StockoutOrderPrintEntity> stockoutOrderPrintEntitys = stockoutOrderPrintDao.selectList(new QueryWrapper<StockoutOrderPrintEntity>().eq("stockout_order_id", stockoutOrderEntity.getOrderId()));
            for (StockoutOrderPrintEntity entity : stockoutOrderPrintEntitys) {
//                System.out.println("打印表"+entity);
                String storName = "";//仓库名称
                StorageEntity storageEntity = storageDao.selectById(entity.getStorId());
                while (storageEntity.getParentId() != 0) {
                    storName = storageEntity.getStorName() + storName;
                    storageEntity = storageDao.selectById(storageEntity.getParentId());
                }
                storName = storageEntity.getStorName() + storName;

                Long goodsCatid = entity.getGoodsCatid();
                //查询出库品的类型、名称、型号、单位
                String catName = new String();
                String goodsName = new String();
                String model = new String();
                String unit = new String();
                if (goodsCatid == 1) {//产品
                    ProductEntity productEntity = productDao.selectById(entity.getGoodsId());
                    catName = "成品";
                    goodsName = productEntity.getProductName();
                    model = productEntity.getProductModel();
                    unit = productEntity.getProductUnit();
                } else if (goodsCatid == 2) {//半成品
                    SemimanufacturesEntity semimanufacturesEntity = semimanufacturesDao.selectById(entity.getGoodsId());
                    catName = "半成品";
                    goodsName = semimanufacturesEntity.getSemiName();
                    model = semimanufacturesEntity.getSemiModel();
                    unit = semimanufacturesEntity.getSemiUnit();
                } else if (goodsCatid == 3) {//原料
                    MaterialEntity materialEntity = materialDao.selectById(entity.getGoodsId());
                    catName = "原料";
                    goodsName = materialEntity.getMatName();
                    model = materialEntity.getMatModel();
                    unit = materialEntity.getMatUnit();
                }
                AgreeStockoutGoodsAndStorInfoRespVo respVo = new AgreeStockoutGoodsAndStorInfoRespVo();
                respVo.setGoodsCatName(catName);
                respVo.setGoodsName(goodsName);
                respVo.setGoodsModel(model);
                respVo.setUnit(unit);
                respVo.setStorName(storName);
                respVo.setAmount(entity.getAmount());
                agreeStockoutGoodsAndStorInfoRespVos.add(respVo);


            }
            agreeStockoutDetailInfoRespVo.setList(agreeStockoutGoodsAndStorInfoRespVos);
            return agreeStockoutDetailInfoRespVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(list);
        return pageUtils;
    }

}