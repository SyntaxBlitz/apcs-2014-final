package com.timothyaveni.apcsfinal.server;

import java.net.InetAddress;

public class ConnectedClient {

	private int id;
	private InetAddress address;
	private int port;

	public ConnectedClient(int id, InetAddress address, int port) {
		this.id = id;
		this.address = address;
		this.port = port;
	}

	public int getId() {
		return this.id;
	}

	public InetAddress getAddress() {
		return this.address;
	}

	public int getPort() {
		return this.port;
	}
}
