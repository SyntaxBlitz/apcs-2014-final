package com.timothyaveni.apcsfinal.client;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;

public class ClientMouseListener implements MouseListener {
	private Client client;

	public ClientMouseListener(Client c) {
		this.client = c;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (client.isInGame()) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				client.getPlayer().useAbility(client.getFrame(), client);
			} else {
				if (!client.getPlayer().isMoving()) {
					client.getPlayer().setStartedAttack(true);
					client.getNetworkThread().sendPacket(
							new SimpleAttackPacket(Client.getNextPacketId(), client.getPlayer().getId(), true));
					client.getPlayer().attack(client);
				}
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
