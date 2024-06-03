package com.lb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.lb.Server;
import com.lb.ServerManager;

public class RoundRobinLoadBalancer implements LoadBalancer{

	public ServerManager serverManager;
	private static AtomicInteger ind = new AtomicInteger(0);

	public RoundRobinLoadBalancer(ServerManager serverManager) {
		this.serverManager = serverManager;
	}

	@Override
	public synchronized Server getServer() {		
		List<Server> listOfServers =new ArrayList<Server> (serverManager.getServerMap().values());
	    return (Server) listOfServers.get(ind.getAndAccumulate(listOfServers.size(), (cur, n)->cur >= n-1 ? 0 : cur+1));
	}

}
