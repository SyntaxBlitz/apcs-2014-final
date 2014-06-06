package com.timothyaveni.apcsfinal.server;

import java.net.InetAddress;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.server.ServerCallbackListener;

public class PrimaryCallbackListener extends ServerCallbackListener {

	private Server server;

	public PrimaryCallbackListener(Server server) {
		this.server = server;
	}

	@Override
	public void bindSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindFail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveFailure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityMoved(EntityLocationPacket p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityDamaged(EntityDamagePacket packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void packetAcknowledged(AcknowledgePacket packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newClientConnected(NewClientPacket packet, InetAddress address, int port) {
		int newClientId = server.getClientList().size();
		if (newClientId > 255) { // uh we can't fit you bro
			return;
		}
		server.getClientList().add(new ConnectedClient(newClientId, address, port));
		server.addPacketToQueue(new NewClientAcknowledgementPacket(Server.getNextPacketId(), newClientId));
	}

}
