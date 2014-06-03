package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.networking.server.ServerThread;

public class Server {

	private int port;

	public Server(int port) {
		PrimaryCallbackListener listener = new PrimaryCallbackListener(this);
		ServerThread thread = new ServerThread(port, listener);

		Thread runThread = new Thread(thread);
		runThread.start();
	}

	public static void main(String[] args) {
		int port = 21102;
		if (args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		Server server = new Server(port);
	}

}
