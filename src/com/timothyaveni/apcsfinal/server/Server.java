package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.networking.server.ServerThread;

public class Server {

	private int port;
	private final double TPS = 30.0;

	public Server(int port) {
		PrimaryCallbackListener listener = new PrimaryCallbackListener(this);
		ServerThread thread = new ServerThread(port, listener);

		Thread runThread = new Thread(thread);
		runThread.start();

		mainLoop();
	}

	private void mainLoop() {
		long currentTick = 0;

		long tickStart;
		while (true) {
			tickStart = System.nanoTime();

			try {
				Thread.sleep(((long) (1000000000 / TPS) - (System.nanoTime() - tickStart)) / 1000000);
			} catch (InterruptedException e) {
			}
			currentTick++;
		}
	}

	public static void main(String[] args) {
		int port = 21102;
		if (args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		Server server = new Server(port);
	}

}
