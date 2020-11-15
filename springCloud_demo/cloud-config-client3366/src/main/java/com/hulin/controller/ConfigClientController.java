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
    @Value("${server.port}")
    private String serverPort;

    /**
     * 如果配置客户端能连接上配置中心 那么就会有config.info配置项
     * @return
     */
    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return "serverPort: "+serverPort+"configInfo:"+configInfo;
    }

}
