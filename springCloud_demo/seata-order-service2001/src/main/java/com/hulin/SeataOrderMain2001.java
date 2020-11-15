package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 取消数据源的自动创建 引入自己创建的数据源配置
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
public class SeataOrderMain2001 {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderMain2001.class,args);
    }
}
/**
 * Sring cloud Seata处理分布式事务
 *      在分布式中，订单服务可能对应订单库，库存服务对应库存库，支付服务对应支付库，则当下订单、支付时
 *      这三个库的事务要保持一致，则引入了分布式事务
 *      Seata是开源的分布式事务解决方案
 *          Seata核心
 *             全局唯一的事务ID+三组件（TC、TM、RM）
 *          Seata安装与运行
 *              1. 下载seata-server-0.9.0.zip 并解压
 *              2.修改conf目录下的file.conf配置文件
 *                  自定义事务组名称、事务日志存储模式改为db、数据库连接信息
 *              3. mysql数据库新建库seata，并建表，建表语句在conf目录下的db_store.sql
 *              4. 修改conf目录下的registry.conf配置文件
 *                     type改为nacos、nacos中的serverAddr
 *              5. 启动nacos
 *              6. 启动seata-server
 * Seata使用场景
 *   业务说明
 *      创建三个服务，一个订单服务、一个库存服务、一个账户服务
 *      当用户下单时，会在订单服务中创建一个订单，然后通过远程调用库存服务来扣减下单商品的库存
 *      再通过远程调用账户服务来扣减用户账户里面的余额，最后在订单服务中修改订单状态为已完成
 *      该操作跨越三个数据库 有两次远程调用 很明显会有分布式事务问题
 * TC:seata服务器
 * TM: 事务的发起方，标有@GlobalTransactional注解的业务方法
 * RM：事务的参与方  每个数据库
 *
 *
 *
 *
 */
