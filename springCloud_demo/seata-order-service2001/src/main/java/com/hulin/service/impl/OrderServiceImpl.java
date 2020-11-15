package com.hulin.service.impl;

import com.hulin.dao.OrderDao;
import com.hulin.domain.Order;
import com.hulin.service.AccountService;
import com.hulin.service.OrderService;
import com.hulin.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther zzyy
 * @create 2020-02-26 15:20
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService
{
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说：下订单->扣库存->减余额->改状态
     * 假设在账户业务类中的扣钱方法中增加延时（睡20s），则调用下单时，会返回请求超时，并且库存会减掉，账户也会减掉 但是订单状态是未完成。
     * 则说明事务没有一致性
     * 解决：
     *      增加seata的@GlobalTransactional注解 相当于开启全局事务
     *      rollbackFor代表发生了任何异常都回滚
     *
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order)
    {
        log.info("----->开始新建订单");
        //1 新建订单
        orderDao.create(order);
        //2 扣减库存
        log.info("----->订单微服务开始调用库存，做扣减Count");
        //通过feign接口调用服务 该处是请求库存服务的controller
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("----->订单微服务开始调用库存，做扣减end");

        //3 扣减账户
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");

        //4 修改订单状态，从零到1,1代表已经完成
        log.info("----->修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("----->修改订单状态结束");

        log.info("----->下订单结束了，O(∩_∩)O哈哈~");

    }
}
