package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton joinServer, options, exit;
	private ImageIcon exitIcon, optionsIcon, joinServerIcon, exitHighlighted, optionsHighlighted, joinServerHighlighted;
	private BufferedImage menuBackground;
	
	public MenuPanel(){
		super();
		joinServer = new MenuButton("E:\\JoinServerButton.png", "E:\\JoinServerButtonHighlighted.png");
		options = new MenuButton("E:\\OptionButton.png", "E:\\OptionButtonHighlighted.png");
		exit = new MenuButton("E:\\ExitButton.png", "E:\\ExitButtonHighlighted.png");
		try {
			exitIcon = new ImageIcon(ImageIO.read(new File("E:\\ExitButton.png")));
			optionsIcon = new ImageIcon(ImageIO.read(new File("E:\\OptionButton.png")));
			joinServerIcon = new ImageIcon(ImageIO.read(new File("E:\\JoinServerButton.png")));
			exitHighlighted = new ImageIcon(ImageIO.read(new File("E:\\ExitButtonHighlighted.png")));
			optionsHighlighted = new ImageIcon(ImageIO.read(new File("E:\\OptionButtonHighlighted.png")));
			joinServerHighlighted = new ImageIcon(ImageIO.read(new File("E:\\JoinServerButtonHighlighted.png")));
			menuBackground = ImageIO.read(new File("E:\\MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setLayout(new FlowLayout());
		
		/*joinServer.setIcon(joinServerIcon);
		exit.setIcon(exitIcon);
		options.setIcon(optionsIcon);
		joinServer.setRolloverIcon(joinServerHighlighted);
		exit.setRolloverIcon(exitHighlighted);
		options.setRolloverIcon(optionsHighlighted);*/
		
		this.add(joinServer);
		this.add(options);
		this.add(exit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joinServer)
			joinServer.setIcon(joinServerHighlighted);
		else if(e.getSource() == options)
			options.setIcon(optionsHighlighted);
		else if(e.getSource() == exit)
			exit.setIcon(exitHighlighted);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(menuBackground, 0, 0, 1024, 768, null);
	}
	
	@Override
	public Dimension getPreferredSize(){
		if (menuBackground == null) {
			return new Dimension(100, 100);
		}	else	{
		return new Dimension(menuBackground.getWidth(), menuBackground.getHeight());
		}
	}
}
