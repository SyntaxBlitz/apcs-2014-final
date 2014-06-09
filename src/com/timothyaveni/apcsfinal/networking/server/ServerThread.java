package com.timothyaveni.apcsfinal.networking.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.timothyaveni.apcsfinal.networking.PacketProcessor;
import com.timothyaveni.apcsfinal.networking.PacketType;
import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;
import com.timothyaveni.apcsfinal.server.ConnectedClient;
import com.timothyaveni.apcsfinal.server.Server;

public class ServerThread implements Runnable {

	private class PacketWithTick {
		Packet packet;
		long tick;
	}

	private final int MAX_PACKET_LENGTH = 512; // in bytes

	private int port;
	private ServerCallbackListener listener;

	private boolean keepRunning = true;

	private HashMap<Integer, PacketWithTick> unacknowledgedPackets = new HashMap<Integer, PacketWithTick>();
	private ArrayList<Integer> alreadyAcknowledgedPackets = new ArrayList<Integer>();

	private DatagramSocket socket;

	Server server;

	public ServerThread(DatagramSocket socket, ServerCallbackListener listener, Server server) {
		this.socket = socket;
		this.listener = listener;
		this.server = server;
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

				callAppropriateCallback(packetObject, receivePacket.getAddress(), receivePacket.getPort());
			}
		} catch (IOException e) {
			listener.receiveFailure();
		}
	}

	private void callAppropriateCallback(Packet packet, InetAddress address, int port) {
		if (packet.isMustAcknowledge()) {
			sendIndividualPacket(new AcknowledgePacket(Server.getNextPacketId(), packet.getId()), address, port);

			// now if we've already received this packet (and the client
			// just didn't get our ack), we can ignore the packet but not before
			// sending out a new ack
			if (alreadyAcknowledgedPackets.contains(packet.getId()))
				return;
			alreadyAcknowledgedPackets.add(packet.getId());
		}

		switch (packet.getPacketType()) {
			case ACKNOWLEDGE:
				packetAcknowledged((AcknowledgePacket) packet);
				break;
			case ENTITY_LOCATION:
				listener.entityMoved((EntityLocationPacket) packet);
				break;
			case ENTITY_DAMAGE:
				listener.entityDamaged((EntityDamagePacket) packet);
				break;
			case NEW_CLIENT:
				listener.newClientConnected((NewClientPacket) packet, address, port);
				break;
			case SIMPLE_ATTACK:
				listener.simpleAttackAnimationUpdated((SimpleAttackPacket) packet);
				break;
			case NEW_ENTITY: // client-only packets
			case NEW_CLIENT_ACKNOWLDEGEMENT:
				break;
		}
	}

	private void packetAcknowledged(AcknowledgePacket packet) {
		this.unacknowledgedPackets.remove(packet.getRemoteId());
	}

	public synchronized void checkUnacknowledgedPackets() {
		Iterator it = unacknowledgedPackets.entrySet().iterator();
		while (it.hasNext()) {
			Entry pair = (Entry) it.next();
			PacketWithTick packet = (PacketWithTick) pair.getValue();
			if (server.getCurrentTick() - packet.tick > 30) { // it has been
																// more
																// than 30 ticks
																// since it was
																// sent
				// sendIndividualPacket(packet.packet);
			}
		}
	}

	public void sendIndividualPacket(Packet packet, InetAddress address, int port) {
		byte[] data = packet.getByteArray();
		DatagramPacket outPacket = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(outPacket);
		} catch (IOException e) {
			// cry
		}
	}

	public DatagramSocket getSocket() {
		return this.socket;
	}
}
