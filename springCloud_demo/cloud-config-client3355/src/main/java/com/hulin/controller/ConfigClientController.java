package com.hulin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {
    @Value("${config.info}") //配置中心中有该项配置
    private String configInfo;

    /**
     * 如果配置客户端能连接上配置中心 那么就会有config.info配置项
     * @return
     */
    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
    /**
     * 分布式配置的动态刷新问题
     *   如果在github上修改了配置文件内容
     *   配置中心和客户端都不重新启动时
     *   当刷新3344 发现配置中心读到最新值
     *   但刷新3355 发现客户端没有读到最新值
     *   3355没有变化 除非自己重启或者重新加载
     *   难道每次修改 客户端都要重启吗
     * 客户端实现动态刷新的步骤
     *      1. pom中引入actuator监控（分布式中除了网关不加 其它都需要加）
     *      2. 修改yml，暴露监控端点
     *      3. 在业务类controller中增加@RefreshScope
     *      4. 需要运维人员发送post请求刷新3355 地址是“http://localhost:3355/actuator/refresh”
     * 上述方案的问题：
     *  假设有多个客户端 那每个微服务都要执行一次post请求，手动刷新？
     *  能否广播，一次通知 处处生效？ 实现自动刷新
     *
     * 实现自动刷新
     *      spring cloud bus配合spring cloud config使用可以实现配置的动态刷新
     *      Bus支持两种消费代理：RabbitMQ和Kafka
     *      spring cloud bus是用来将分布式系统的节点与轻量级消息系统链接起来的框架，它整合了java的事件处理机制和消息中间件的功能。
     *      两种方式
     *          1.触发某个客户端/bus/refresh，然后改客户端发送消息到spring cloud bus，最后其余所有客户端再从bus接收消息。利用消息总线触发一个客户端/bus/refresh，从而刷新所有客户端的配置
     *          2.触发配置中心/bus/refresh，然后配置中心发送消息到spring cloud bus，最后所有客户端再从bus接收消息。利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置
     *          采取方式2最佳
     *      步骤：
     *          1.配置中心服务端添加消息总线支持 pom、yml
     *          2.所有客户端添加消息总线支持 pom、yml
     *          3.运维人员对配置中心发送post请求 地址http://localhost:3344/actuator/bus-refresh
     *          则所有客户端都会读取到最新配置
     *      动态刷新定点通知
     *          现在只想通知部分客户端 不想全部通知
     *          则http://localhost:3344/actuator/bus-refresh/config-client:3355
     *          config-client是微服务名称
     * 什么是总线
     *      在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。
     *      由于该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线。在总线上的各个实例，都可以方便地广播一些需要
     *      让其它连接在该主题上的实例都知道的消息
     *
     *
     *
     */

    /**
     * Spring Cloud Stream（消息驱动）
     *      为什么引入Cloud Stream
     *          前端vue和中端javaEE平台交互产生的数据传输给后端大数据平台处理，而javaEE习惯使用RabbitMQ消息中间件，
     *          大数据习惯使用Kafka消息中间件。则一个系统里面可能存在两种MQ，那么切换、维护、开发都相当困难
     *          而Spring Cloud Stream可以让我们不再关注具体MQ的细节，我们只需要用一种适配绑定的方式，自动的给我们在各种MQ内切换
     *          相当于只需要操作Cloud Stream即可，就可以在各种MQ中进行切换使用
     *      消息驱动就是屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型
     *
     *
     */
}
