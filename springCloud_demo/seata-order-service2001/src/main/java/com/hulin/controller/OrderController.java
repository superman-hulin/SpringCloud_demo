package com.hulin.controller;

import com.hulin.domain.CommonResult;
import com.hulin.domain.Order;
import com.hulin.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther zzyy
 * @create 2020-02-26 15:24
 */
@RestController
public class OrderController
{
    @Resource
    private OrderService orderService;

    /**
     * 注意该处为什么是get请求，因为这个是下订单入口请求 是给用户使用的
     * 用户是通过浏览器访问该接口
     * @param order
     * @return
     */
    @GetMapping("/order/create")
    public CommonResult create( Order order)
    {
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }
}
