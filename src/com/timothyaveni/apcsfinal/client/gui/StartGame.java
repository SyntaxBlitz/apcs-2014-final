package com.timothyaveni.apcsfinal.client.gui;

public class StartGame {
	
	public static void main(String[] args){
		GameFrame newGame = new GameFrame("Saviors of Gundthor");
		MenuPanel menu = new MenuPanel();
		MapCanvas map = new MapCanvas();
		newGame.changeFrame(menu);
		//newGame.close();
		//newGame.changeFrame(map);
	}
}
