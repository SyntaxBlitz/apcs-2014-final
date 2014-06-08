package com.timothyaveni.apcsfinal.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.FileReader;
import com.timothyaveni.apcsfinal.client.Map;
import com.timothyaveni.apcsfinal.client.MapMetadata;
import com.timothyaveni.apcsfinal.networking.WorldSectionID;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.server.ServerThread;

public class Server {

	private int port;
	private final double TPS = 30.0;

	private ArrayList<ConnectedClient> clientList = new ArrayList<ConnectedClient>();
	private static ArrayList<Packet> packetQueue = new ArrayList<Packet>();

	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	private HashMap<Integer, MapMetadata> loadedMaps = new HashMap<Integer, MapMetadata>();
	int lastEntityId = -1;

	private ServerThread thread;

	private DatagramSocket socket;

	private long currentTick = 0;

	private static int lastLocalPacketId = 0;

	public Server(int port) {
		PrimaryCallbackListener listener = new PrimaryCallbackListener(this);

		try {
			socket = new DatagramSocket(port);
			listener.bindSuccess();
		} catch (SocketException e) {
			listener.bindFail();
		}

		thread = new ServerThread(socket, listener, this);

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
		long tickStart;
		
		// load in first map
		loadedMaps.put(1, new MapMetadata(FileReader.getFileFromResourceString(WorldSectionID.getMapNameFromID(1)), 1));
		
		while (true) {
			tickStart = System.nanoTime();

			Packet[] sendPackets = packetQueue.toArray(new Packet[packetQueue.size()]);
			packetQueue.clear();
			for (int i = 0; i < sendPackets.length; i++) {
				Packet packet = sendPackets[i];
				byte[] data = packet.getByteArray();
				for (ConnectedClient client : clientList) {
					DatagramPacket outPacket = new DatagramPacket(data, data.length, client.getAddress(),
							client.getPort());
					try {
						socket.send(outPacket);
					} catch (IOException e) {
						// cry
					}
				}
			}

			packetQueue.clear(); // yeah so initially I forgot this code. it was really ugly

			thread.checkUnacknowledgedPackets();

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

	public long getCurrentTick() {
		return currentTick;
	}

	public HashMap<Integer, Entity> getEntityList() {
		return this.entities;
	}
	
	public HashMap<Integer, MapMetadata> getLoadedMaps() {
		return this.loadedMaps;
	}
	
	public void loadMap() {
		
	}

	public Map getMap(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
