package com.timothyaveni.apcsfinal.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class KeyboardInputTest {
	
	public static JFrame frame = new JFrame();
	public static JLabel label = new JLabel();
	
	public static void main(String[] args){
		KeyListener listener = new KeyListener(){
			public void keyTyped(KeyEvent e){
				
			}
			public void keyPressed(KeyEvent e){
				label.setText("" + e.getKeyCode());
			}
			public void keyReleased(KeyEvent e){}
		};
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.addKeyListener(listener);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
