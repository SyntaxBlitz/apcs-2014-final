package com.timothyaveni.apcsfinal.server;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.PlayerLocationPacket;
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
	public void playerMoved(PlayerLocationPacket p) {
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

}
