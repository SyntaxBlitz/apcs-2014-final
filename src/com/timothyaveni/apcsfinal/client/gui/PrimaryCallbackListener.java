package com.timothyaveni.apcsfinal.client.gui;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.ClientCallbackListener;
import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;

public class PrimaryCallbackListener extends ClientCallbackListener {
	
	private Client client;
	
	public PrimaryCallbackListener(Client client) {
		this.client = client;
	}

	@Override
	public void receiveFailure() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void entityMoved(EntityLocationPacket packet) {
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
	public void clientConnectionAcknowldged(NewClientAcknowledgementPacket packet) {
		// TODO Auto-generated method stub

	}

}
