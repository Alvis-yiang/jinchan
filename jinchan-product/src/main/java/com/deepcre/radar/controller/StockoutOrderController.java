package com.deepcre.radar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.deepcre.radar.feign.RenrenFastFeignService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.StockoutOrderEntity;
import com.deepcre.radar.service.StockoutOrderService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;


/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-02-17 14:29:13
 */
@RestController
@RequestMapping("radar/stockoutorder")
public class StockoutOrderController {

    @Autowired
    private StockoutOrderService stockoutOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:stockoutorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockoutOrderService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     *新增出库单
     */
    @RequestMapping("/createorder/{empId}")
    public R createOrder(@RequestBody Long[] stockoutIds,@PathVariable("empId")Long empId){
        String result = stockoutOrderService.createOrder(stockoutIds,empId);

        return R.ok().put("result", result);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{oderId}")
    @RequiresPermissions("generator:stockoutorder:info")
    public R info(@PathVariable("oderId") String oderId){
		StockoutOrderEntity stockoutOrder = stockoutOrderService.getById(oderId);

        return R.ok().put("stockoutOrder", stockoutOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:stockoutorder:save")
    public R save(@RequestBody StockoutOrderEntity stockoutOrder){
		stockoutOrderService.save(stockoutOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:stockoutorder:update")
    public R update(@RequestBody StockoutOrderEntity stockoutOrder){
		stockoutOrderService.updateById(stockoutOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:stockoutorder:delete")
    public R delete(@RequestBody String[] oderIds){
		stockoutOrderService.removeByIds(Arrays.asList(oderIds));

        return R.ok();
    }

    /**
     * 审核出库单
     * @param stockoutOrder
     * @return
     */
    @RequestMapping("/audit")
//    @RequiresPermissions("generator:stockoutorder:update")
    public R audit(@RequestBody StockoutOrderEntity stockoutOrder){
        stockoutOrder.setAuditDate(new Date());
        stockoutOrderService.audit(stockoutOrder);

        return R.ok();
    }

    /**
     * 出库单打印
     * @param params
     * @return
     */
    @RequestMapping("/print")
    public R printStockoutOrder(@RequestParam Map<String, Object> params){
        PageUtils page = stockoutOrderService.printStockoutOrder(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/printStream")
    public void printStream(HttpServletRequest request, HttpServletResponse response){
        String fileName = "static/web/compressed.tracemonkey-pldi-09.pdf";
        URL url = null;
        InputStream fis = null;
        try{
            //打开请求连接
            url = new URL(fileName);
            URLConnection connection = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) connection;
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.connect();
            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName));
            response.setContentType("application/octet-stream");
            response.setContentType("application/pdf;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin","*");
            ServletOutputStream sos = response.getOutputStream();
            fis=conn.getInputStream();
            int b;
            while ((b=fis.read())!=-1){
                sos.write(b);
            }
            sos.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
