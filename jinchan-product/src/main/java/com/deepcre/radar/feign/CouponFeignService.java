package com.deepcre.radar.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author alvis-yiang
 * @create 2022-01-05 1:50 PM
 */

@FeignClient("jinchan-coupon")
public interface CouponFeignService {
}
