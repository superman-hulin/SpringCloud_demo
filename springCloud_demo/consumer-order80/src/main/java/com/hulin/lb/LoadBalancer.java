package com.hulin.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 该接口中的方法用于收集某服务名中所有的服务实例
 */
public interface LoadBalancer {
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
