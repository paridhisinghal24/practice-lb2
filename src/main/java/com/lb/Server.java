package com.lb;

import java.util.Objects;

public class Server {
	private final String ipAddress;
	private String name;
	
	public Server(String ipAddress, String name) {
		this.ipAddress = ipAddress;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ipAddress);

//		return Objects.hash(ipAddress, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Server other = (Server) obj;
		return Objects.equals(ipAddress, other.ipAddress);

//		return Objects.equals(ipAddress, other.ipAddress) && Objects.equals(name, other.name);
	}
	
	
}
