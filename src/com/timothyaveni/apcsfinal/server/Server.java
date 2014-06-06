package com.timothyaveni.apcsfinal.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.server.ServerThread;

public class Server {

	private int port;
	private final double TPS = 30.0;

	private ArrayList<ConnectedClient> clientList = new ArrayList<ConnectedClient>();
	private static ArrayList<Packet> packetQueue = new ArrayList<Packet>();

	private ServerThread thread;

	private DatagramSocket socket;

	private static int lastLocalPacketId = 0;

	public Server(int port) {
		PrimaryCallbackListener listener = new PrimaryCallbackListener(this);

		try {
			socket = new DatagramSocket(port);
			listener.bindSuccess();
		} catch (SocketException e) {
			listener.bindFail();
		}

		thread = new ServerThread(socket, listener);

		Thread runThread = new Thread(thread);
		runThread.start();

		mainLoop();
	}

	public ArrayList<ConnectedClient> getClientList() {
		return clientList;
	}

	public ServerThread getThread() { // triangular dependency? you bet your
										// ass.
		return this.thread;
	}

	private void mainLoop() {
		long currentTick = 0;

		long tickStart;
		while (true) {
			tickStart = System.nanoTime();

			for (Packet packet : packetQueue) {
				for (ConnectedClient client : clientList) {
					byte[] data = packet.getByteArray();
					DatagramPacket outPacket = new DatagramPacket(data, data.length, client.getAddress(),
							client.getPort());
					try {
						socket.send(outPacket);
					} catch (IOException e) {
						// cry
					}
				}
			}

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

	public synchronized static int getNextPacketId() {
		return lastLocalPacketId++; // starts at 0, so we can just
									// increment afterwards
	}

	public static void addPacketToQueue(Packet packet) {
		packetQueue.add(packet);
	}

}
