package com.timothyaveni.apcsfinal.client;

public class Client {
	private static final double FPS = 30.0;
	private boolean[] keyboard = new boolean[4];
	private ArrayList<> entities = new ArrayList<Entity>();
	private KeyListener keyListener = new KeyListener(){
		public void keyTyped(KeyEvent e){}
		public void keyPressed(KeyEvent e){
			switch(e.getKeyCode()){
			case 39:
				keyboard[0] = true;
				break;
			case 38:
				keyboard[1] = true;
				break;
			case 37:
				keyboard[2] = true;
				break;
			case 40:
				keyboard[3] = true;
				break;
			}
			
		}
		public void keyReleased(KeyEvent e){
			switch(e.getKeyCode()){
			case 39:
				keyboard[0] = false;
				break;
			case 38:
				keyboard[1] = false;
				break;
			case 37:
				keyboard[2] = false;
				break;
			case 40:
				keyboard[3] = false;
				break;
			}
		}
	}
	
	/**
	 * render environment
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// display GUI
		gameLoop(true);
	}

	public static void gameLoop(boolean isRunning) {
		long lastLoopTime;

		while (isRunning) {
			lastLoopTime = System.nanoTime();

			// get player input
			// move sprites
			// render environment

			try {
				Thread.sleep((long) (1000000000 / FPS - (System.nanoTime() - lastLoopTime)));

			} catch (InterruptedException e) {
			}

		}
	}

}
