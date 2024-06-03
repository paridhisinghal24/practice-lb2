package com.lb.service;

import com.lb.Server;
import com.lb.ServerManager;

public interface LoadBalancer {
	public Server getServer();
}
