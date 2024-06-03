package com.lb.service;

import com.lb.model.Server;

public class LoadBalancerContext {
	
	private LoadBalancer loadBalancer;
	
	
	public LoadBalancerContext(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
	
	public Server getServer() {
		return loadBalancer.getServer();
	}
	
}
