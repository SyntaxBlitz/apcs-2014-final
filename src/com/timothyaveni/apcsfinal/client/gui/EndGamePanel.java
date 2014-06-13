package com.timothyaveni.apcsfinal.client.gui;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.timothyaveni.apcsfinal.client.FileReader;

public class EndGamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private PanelTest frame;
	private MenuButton exit;
	private int counter;
	private Timer timer;
	BufferedImage image;
	private float alpha = 1f;

	public EndGamePanel(PanelTest frame) {
		super();
		
		counter = 100;
		this.frame = frame;

		this.setLayout(null);

		exit = new MenuButton("ExitButton");
		exit.setBounds(388, 600, 256, 64);
		exit.addActionListener(this);
		exit.setEnabled(false);
		
		try {
			image = ImageIO.read(FileReader.getFileFromResourceString("EndGame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.add(exit);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			frame.close();
			return;
		}
		counter --;
		if (counter >= 0) {
			//image = ImageModifier.fadeIn(image, 0, 0, 1024, 768, Colors.ALPHA_RGB, 0.009F);
			this.repaint();
		} else {
			exit.setEnabled(true);
			timer.stop();
			this.repaint();
			System.out.println("Done");
		}
	}

	public void sceneLoop() {
		timer = new Timer(50, this);
		timer.start();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawImage(image, 0, 0, this);
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(1f - alpha));
        g2d.drawImage(image, 0, 0, this);
        alpha -= 0.009;
	}

	// makes sure the JPanel is the correct size
	@Override
	public Dimension getPreferredSize() {
		if (image == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(image.getWidth(), image.getHeight());
		}
	}

	
	public static void main(String[] args){
		PanelTest frame = new PanelTest("Saviors of Gundthor");
		EndGamePanel endGame = new EndGamePanel(frame);
		frame.changeFrame(endGame);
		endGame.sceneLoop();
	}
}
