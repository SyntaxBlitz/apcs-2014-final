package com.timothyaveni.apcsfinal.client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;

public class ClientMouseListener implements MouseListener {
	private Client client;

	public ClientMouseListener(Client c) {
		this.client = c;
	}

	/*
	 * this will trigger the attack method but I have no idea what to call it
	 * off of
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (client.isInGame()) {
			if (!client.getPlayer().isMoving()) {
				client.getPlayer().setStartedAttack(true);
				client.getNetworkThread().sendPacket(
						new SimpleAttackPacket(Client.getNextPacketId(), client.getPlayer().getId(), true));
				client.getPlayer().attack(client);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (client.isInGame()) {
			client.getPlayer().setStartedAttack(false);
			client.getNetworkThread().sendPacket(
					new SimpleAttackPacket(Client.getNextPacketId(), client.getPlayer().getId(), false));
		}
	}

}