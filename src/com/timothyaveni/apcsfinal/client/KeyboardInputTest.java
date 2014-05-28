package com.timothyaveni.apcsfinal.client;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class KeyboardInputTest {
	
	public static void main(String[] args){
		KeyListener listener = new KeyListener(){
			public void keyTyped(KeyEvent e){
				System.out.print(KeyEvent.getKeyText(e.getKeyCode()));
			}
			public void keyPressed(KeyEvent e){
				System.out.print(KeyEvent.getKeyText(e.getKeyCode()));
			}
			public void keyReleased(KeyEvent e){}
		};
		JFrame frame = new JFrame();
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
