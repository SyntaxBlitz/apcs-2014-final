package com.timothyaveni.apcsfinal.networking.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.timothyaveni.apcsfinal.networking.PacketProcessor;
import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.packet.PlayerLocationPacket;

public class ServerThread implements Runnable {

	private final int MAX_PACKET_LENGTH = 512; // in bytes

	private int port;
	private ServerCallbackListener listener;

	private DatagramSocket socket;

	private boolean keepRunning = true;

	private static int lastLocalPacketId = 0;

	public ServerThread(int port, ServerCallbackListener listener) {
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run() {
		if (listener == null) {
			System.out.println("Cannot run server thread without assigning callback listener");
			return;
		}

		try {
			socket = new DatagramSocket(port);
			listener.bindSuccess();

			while (keepRunning) {
				byte[] buffer = new byte[MAX_PACKET_LENGTH];
				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(receivePacket);

				PacketProcessor processor = new PacketProcessor(receivePacket.getData(), getNextPacketId());
				callAppropriateCallback(processor.getPacket());
			}
		} catch (SocketException ex) {
			listener.bindFail();
		} catch (IOException e) {
			listener.receiveFailure();
		}
	}

	private void callAppropriateCallback(Packet packet) {
		switch (packet.getPacketType()) {
			case ACKNOWLEDGE:
				// TODO: instead of forcing this on the listener, keep track in
				// ServerThread
				listener.packetAcknowledged((AcknowledgePacket) packet);
				break;
			case PLAYER_LOCATION:
				listener.playerMoved((PlayerLocationPacket) packet);
				break;
			case ENTITY_DAMAGE:
				listener.entityDamaged((EntityDamagePacket) packet);
				break;
		}
	}

	// this doesn't strictly need to be synchronized because it's being called
	// in a blocking loop anyway. Mostly this is just in case we refactor later.
	public synchronized static int getNextPacketId() {
		return lastLocalPacketId++; // starts at 0, so we can just
									// increment afterwards
	}

}
