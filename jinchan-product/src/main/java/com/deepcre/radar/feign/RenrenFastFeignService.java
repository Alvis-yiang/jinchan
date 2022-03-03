package com.deepcre.radar.feign;

import com.deepcre.common.utils.R;
import com.deepcre.radar.feign.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: alvis-yiang
 * @Date: Created in 14:21 2022/2/18
 * @Description:
 */
@FeignClient("renren-fast")
public interface RenrenFastFeignService {

    /**
     * 根据id获取用户信息
     */
    @GetMapping("/renren-fast/sys/user/userinfo/{empId}")
    public String getInfoById(@PathVariable("empId") Long empId);
}
