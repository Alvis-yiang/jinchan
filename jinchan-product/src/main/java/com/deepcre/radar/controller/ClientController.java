package com.deepcre.radar.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepcre.radar.entity.ClientEntity;
import com.deepcre.radar.service.ClientService;
import com.deepcre.common.utils.PageUtils;
import com.deepcre.common.utils.R;



/**
 * 
 *
 * @author alvis-yiang
 * @email adolphyanghao@163.com
 * @date 2022-01-19 15:40:49
 */
@RestController
@RequestMapping("radar/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("radar:client:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = clientService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{clientId}")
    @RequiresPermissions("radar:client:info")
    public R info(@PathVariable("clientId") Long clientId){
		ClientEntity client = clientService.getById(clientId);

        return R.ok().put("client", client);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("radar:client:save")
    public R save(@RequestBody ClientEntity client){
		clientService.save(client);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("radar:client:update")
    public R update(@RequestBody ClientEntity client){
		clientService.updateById(client);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("radar:client:delete")
    public R delete(@RequestBody Long[] clientIds){
		clientService.removeByIds(Arrays.asList(clientIds));

        return R.ok();
    }

}
