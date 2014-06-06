package com.timothyaveni.apcsfinal.client;

import java.util.HashMap;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;

public class PrimaryCallbackListener extends ClientCallbackListener {
	
	private Client client;
	
	// key is entity id, value is the id of the last Location packet we've
	// gotten about that entity.
	private HashMap<Integer, Integer> lastEntityLocationId = new HashMap<Integer, Integer>();
	
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
	public void clientConnectionAcknowldged(NewClientAcknowledgementPacket packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newEntity(NewEntityPacket packet) {
		// TODO Auto-generated method stub
		
	}

}
