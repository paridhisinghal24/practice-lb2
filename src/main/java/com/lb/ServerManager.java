package com.lb;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {
	
	private Map<String, Server> serverMap;

	public ServerManager() {
		serverMap = new ConcurrentHashMap<String, Server>();
	}
	
	public synchronized void addServer(Server server) {
		serverMap.putIfAbsent(server.getIpAddress(),server);
	}
	
	public void removeServer(Server server) {
		serverMap.remove(server.getIpAddress());
	}

	public Map<String, Server> getServerMap() {
		return serverMap;
	}

	public void setServerMap(Map<String, Server> serverMap) {
		this.serverMap = serverMap;
	}
	
}
