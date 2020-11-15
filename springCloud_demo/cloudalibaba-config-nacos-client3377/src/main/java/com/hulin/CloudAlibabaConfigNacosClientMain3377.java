package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Nacos同springcloud-config一样，在项目初始化时，要保证先从配置中心进行配置拉取
 * 拉取配置之后，才能保证项目的正常启动
 * 而和springcloud-config不同的是，nacos配置中心的配置直接在nacos中，不用在github上
 * nacos上的配置规则
 *      dataId为${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
 *      现在更改配置中心中的配置 3377会自动刷新配置 无需启动
 * springboot中配置文件的加载是存在优先级顺序的，bootstrap优先级高于application
 * Nacos支持AP和CP模式的切换
 * Nacos集群和持久化配置
 *      Nacos集群需要持久化配置的支持，因为nacos作为配置中心，nacos中的数据需要在数据库中持久化
 *      每个Nacos节点都是自带的嵌入式数据库derby，而现在想要集群的话，就需要每个Nacos节点都共享同样的数据
 *      则需要公共的数据库做持久化，Nacos默认支持mysql来做持久化配置
 *     持久化配置流程：
 *          1.执行nacos-mysql.sql 创建数据库
 *          2.在nacos-server-1.1.4\nacos\conf目录下找到application.properties中增加mysql数据库的配置 不再使用内置数据库
 *          3.重启nacos
 *     Nginx集群---》nacos集群---》mysql集群  相当于nacos配置中心的地址通过Nginx来反向代理
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaConfigNacosClientMain3377 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaConfigNacosClientMain3377.class,args);
    }
}

