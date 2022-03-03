package com.deepcre.radar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: alvis-yiang
 * @Date: Created in 15:04 2022/2/19
 * @Description:
 */
@FeignClient("jinchan-employee")
public interface EmployeeFeignService {

    /**
     * 根据id获取用户信息
     */
    @GetMapping("/jinchanemployee/sysuser/userinfo/{empId}")
    public String getInfoById(@PathVariable("empId") Long empId );

}
