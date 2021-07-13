package com.epam.gateway.config;

import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class HeaderRoutingKeyRule extends RoundRobinRule {

    private static final String ROUTING_KEY = "routingKey";

    @Override
    public Server choose(Object o) {
        //Eureka instances custom metadata

        List<Server> serverList = getLoadBalancer().getReachableServers();

        for (int i = 0; i < serverList.size(); i++) {
            serverList.get(i).setZone("v" + i);
        }

        String routingHeader = getRoutingHeader();
        if (StringUtils.isNotBlank(routingHeader)) {
            for (Server server : serverList) {
                if (routingHeader.equals(String.valueOf(server.getZone()))) {
                    return server;
                }
            }
        }
        return super.choose(o);
    }

    private String getRoutingHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader(ROUTING_KEY);
    }

}
