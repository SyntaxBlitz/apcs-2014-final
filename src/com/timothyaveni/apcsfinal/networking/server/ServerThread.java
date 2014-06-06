package com.timothyaveni.apcsfinal.networking.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.timothyaveni.apcsfinal.networking.PacketProcessor;
import com.timothyaveni.apcsfinal.networking.PacketType;
import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.server.ConnectedClient;
import com.timothyaveni.apcsfinal.server.Server;

public class ServerThread implements Runnable {

	private final int MAX_PACKET_LENGTH = 512; // in bytes

	private int port;
	private ServerCallbackListener listener;

	private boolean keepRunning = true;

	private DatagramSocket socket;

	public ServerThread(DatagramSocket socket, ServerCallbackListener listener) {
		this.socket = socket;
		this.listener = listener;
	}

	@Override
	public void run() {
		if (listener == null) {
			System.out.println("Cannot run server thread without assigning callback listener");
			return;
		}

		try {
			while (keepRunning) {
				byte[] buffer = new byte[MAX_PACKET_LENGTH];
				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(receivePacket);

				PacketProcessor processor = new PacketProcessor(receivePacket.getData(), Server.getNextPacketId());
				Packet packetObject = processor.getPacket();

				// special case, because it needs address/port
				if (packetObject.getPacketType() == PacketType.NEW_CLIENT) {
					listener.newClientConnected((NewClientPacket) packetObject, receivePacket.getAddress(),
							receivePacket.getPort());
				} else {
					callAppropriateCallback(packetObject);
				}
			}
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
			case ENTITY_LOCATION:
				listener.entityMoved((EntityLocationPacket) packet);
				break;
			case ENTITY_DAMAGE:
				listener.entityDamaged((EntityDamagePacket) packet);
				break;
			case NEW_CLIENT: // special case
				break;
			case NEW_ENTITY: // client-only packets
			case NEW_CLIENT_ACKNOWLDEGEMENT:
				break;
		}
	}

}
