package com.timothyaveni.apcsfinal.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.timothyaveni.apcsfinal.networking.PacketProcessor;
import com.timothyaveni.apcsfinal.networking.PacketType;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.server.Server;

public class ClientNetworkThread implements Runnable {

	private static final int MAX_PACKET_LENGTH = 512;
	
	private DatagramSocket socket;
	private ClientCallbackListener listener;
	
	public ClientNetworkThread(DatagramSocket socket, ClientCallbackListener listener) {
		this.socket = socket;
		this.listener = listener;
	}
	
	@Override
	public void run() {
		if (listener == null) {
			System.out.println("Cannot run client thread without assigning callback listener");
			return;
		}
		
		boolean keepRunning = true;
		
		try {
			while (keepRunning) {
				byte[] buffer = new byte[MAX_PACKET_LENGTH];
				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(receivePacket);

				//PacketProcessor processor = new PacketProcessor(receivePacket.getData(), Client.getNextPacketId());
				//Packet packetObject = processor.getPacket();
			}
		} catch (IOException e) {
			listener.receiveFailure();
		}
		
	}
	
}
