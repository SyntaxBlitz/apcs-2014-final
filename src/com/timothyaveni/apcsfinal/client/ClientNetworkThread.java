package com.timothyaveni.apcsfinal.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.timothyaveni.apcsfinal.networking.PacketProcessor;
import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;

public class ClientNetworkThread implements Runnable {

	private class PacketWithFrame {
		Packet packet;
		long frame;
	}

	private static final int MAX_PACKET_LENGTH = 512;
	private static final int PORT = 21102;

	private DatagramSocket socket;
	private ClientCallbackListener listener;

	private HashMap<Integer, PacketWithFrame> unacknowledgedPackets = new HashMap<Integer, PacketWithFrame>();
	private ArrayList<Integer> alreadyAcknowledgedPackets = new ArrayList<Integer>();
	private Client client;

	public ClientNetworkThread(DatagramSocket socket, ClientCallbackListener listener, Client client) {
		this.socket = socket;
		this.listener = listener;
		this.client = client;
	}

	@Override
	public void run() {
		System.out.println("Starting network thread");
		if (listener == null) {
			System.out.println("Cannot run client thread without assigning callback listener");
			return;
		}

		sendPacket(new NewClientPacket(Client.getNextPacketId(), client.getPlayerType()));

		boolean keepRunning = true;

		try {
			while (keepRunning) {
				byte[] buffer = new byte[MAX_PACKET_LENGTH];
				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(receivePacket);

				PacketProcessor processor = new PacketProcessor(receivePacket.getData(), Client.getNextPacketId());
				callAppropriateCallback(processor.getPacket());
			}
		} catch (IOException e) {
			listener.receiveFailure();
		}
	}

	private void callAppropriateCallback(Packet packet) {
		// System.out.println("got a packet: " + packet.getPacketType());
		if (packet.isMustAcknowledge()) {
			// System.out.println("gotta acknowledge");
			sendPacket(new AcknowledgePacket(Client.getNextPacketId(), packet.getId()));

			// now if we've already received this packet (and the server
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
			case NEW_ENTITY:
				listener.newEntity((NewEntityPacket) packet);
				break;
			case NEW_CLIENT_ACKNOWLDEGEMENT:
				listener.clientConnectionAcknowldged((NewClientAcknowledgementPacket) packet);
				break;
			case SIMPLE_ATTACK:
				listener.simpleAttackAnimationUpdated((SimpleAttackPacket) packet);
				break;
			case NEW_CLIENT: // server-only packet
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
			PacketWithFrame packet = (PacketWithFrame) pair.getValue();
			if (client.getFrame() - packet.frame > 30) { // it has been more
															// than 30 frames
				// since it was sent
				sendPacket(packet.packet);
			}
		}
	}

	public void sendPacket(Packet packet) {
		byte[] data = packet.getByteArray();
		DatagramPacket sendPacket = new DatagramPacket(data, data.length, client.getRemoteInetAddress(), PORT);
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Integer> getAlreadyAcknowledgedPackets() {
		return alreadyAcknowledgedPackets;
	}

}
