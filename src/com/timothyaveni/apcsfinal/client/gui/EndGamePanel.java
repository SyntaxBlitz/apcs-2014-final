package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class EndGamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private PanelTest frame;
	private MenuButton exit;
	private JPanel transparency;
	private int counter;
	private Timer timer;

	public EndGamePanel(PanelTest frame) {
		super();

		counter = 100;
		this.frame = frame;

		this.setLayout(null);

		exit = new MenuButton("ExitButton");
		transparency = new JPanel();
		exit.setBounds(388, 600, 256, 64);
		transparency.setBounds(0, 0, 1024, 768);
		transparency.setBackground(new Color(0, 0, 0, 100));
		exit.addActionListener(this);

		this.add(transparency);
		this.add(exit);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			frame.close();
			return;
		}
		counter -= .25;
		if (counter >= 0) {
			transparency.setBackground(new Color(0, 0, 0, counter));
			this.repaint();
		} else {
			timer.stop();
			this.repaint();
			System.out.println("Done");
		}
	}

	public void sceneLoop() {
		timer = new Timer(100, this);
		timer.start();
	}
	
	public static void main(String[] args){
		PanelTest frame = new PanelTest("Saviors of Gundthor");
		EndGamePanel endGame = new EndGamePanel(frame);
		frame.changeFrame(endGame);
		endGame.sceneLoop();
	}
}
