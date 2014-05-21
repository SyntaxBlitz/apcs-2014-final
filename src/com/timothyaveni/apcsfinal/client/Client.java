package com.timothyaveni.apcsfinal.client;

public class Client {
	private static final double FPS = 30.0;
	/**render environment 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//display GUI
		gameLoop(true);		
	}
	
	public static void gameLoop (boolean isRunning) {
		long lastLoopTime;
		
		while(isRunning){
			lastLoopTime = System.nanoTime();
			
			//get player input
			//move sprites
			//render environment
			
			try{Thread.sleep((long)(1000000000 / FPS - (System.nanoTime() - lastLoopTime)));
			
			} catch(InterruptedException e){}
			
		}
	}
	
}
