package com.timothyaveni.apcsfinal.client.gui;

public class StartGame {

	public static void main(String[] args) {
		GameFrame newGame = new GameFrame("Saviors of Gundthor");
		MenuPanel menu = new MenuPanel(newGame);
		newGame.changeFrame(menu);
	}
}
