package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.ClientNetworkThread;
import com.timothyaveni.apcsfinal.client.FileReader;
import com.timothyaveni.apcsfinal.client.Map;
import com.timothyaveni.apcsfinal.client.PrimaryCallbackListener;
import com.timothyaveni.apcsfinal.networking.EntityType;

public class ChooseCharacterPanel extends JPanel implements ActionListener, UsesClient {

	private static final long serialVersionUID = 7258173020280356581L;
	private Client client;
	private GameFrame frame;

	private MenuButton pickTank;
	private MenuButton pickHealer;
	private MenuButton pickArcher;
	private MenuButton pickMage;
	private MenuButton pickRogue;
	private MenuButton connectButton;

	private JLabel characterView;

	private JLabel serverIpLabel;
	private JTextField serverIpField;

	private EntityType selectedPlayerType = EntityType.TANK;

	private BufferedImage menuBackground;

	public ChooseCharacterPanel(GameFrame frame) {
		super();
		this.frame = frame;
		this.setPreferredSize(new Dimension(Client.WIDTH, Client.HEIGHT));

		this.setLayout(null);

		characterView = new JLabel();

		updateCharacterView();

		pickTank = new MenuButton("PickTankButton");
		pickHealer = new MenuButton("PickHealerButton");
		pickArcher = new MenuButton("PickArcherButton");
		pickMage = new MenuButton("PickMageButton");
		pickRogue = new MenuButton("PickRogueButton");

		serverIpLabel = new JLabel("Server IP:");
		serverIpField = new JTextField();

		connectButton = new MenuButton("ConnectButton");

		try {
			menuBackground = ImageIO.read(FileReader.getFileFromResourceString("MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		characterView.setBounds(Client.WIDTH / 2 - 32 / 2, 192, 32, 48);

		pickTank.setBounds(Client.WIDTH / 2 - 128 / 2, 256, 128, 32);
		pickHealer.setBounds(Client.WIDTH / 2 - 128 / 2, 288, 128, 32);
		pickArcher.setBounds(Client.WIDTH / 2 - 128 / 2, 320, 128, 32);
		pickMage.setBounds(Client.WIDTH / 2 - 128 / 2, 352, 128, 32);
		pickRogue.setBounds(Client.WIDTH / 2 - 128 / 2, 384, 128, 32);

		serverIpLabel.setBounds(Client.WIDTH / 2 - 128 / 2, 466, 128, 16);
		serverIpField.setBounds(Client.WIDTH / 2 - 128 / 2, 484, 128, 32);

		connectButton.setBounds(Client.WIDTH / 2 - 256 / 2, 572, 256, 64);

		pickTank.addActionListener(this);
		pickHealer.addActionListener(this);
		pickArcher.addActionListener(this);
		pickMage.addActionListener(this);
		pickRogue.addActionListener(this);

		connectButton.addActionListener(this);

		this.add(characterView);

		this.add(pickTank);
		this.add(pickHealer);
		this.add(pickArcher);
		this.add(pickMage);
		this.add(pickRogue);

		this.add(serverIpLabel);
		this.add(serverIpField);

		this.add(connectButton);
	}

	private void loadIntoGame(InetAddress address, EntityType playerType) {
		DatagramSocket s = null;
		try {
			s = new DatagramSocket();
		} catch (SocketException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		client.setSocket(s);
		client.setRemoteInetAddress(address);

		client.setPlayerType(playerType);

		client.setNetworkThread(new ClientNetworkThread(s, new PrimaryCallbackListener(client), client));

		client.setCurrentMap(new Map(1));

		frame.close();
		frame.changeFrame(new MapCanvas());

		frame.getMapCanvas().setReadyToRender(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(menuBackground, 0, 0, Client.WIDTH, Client.HEIGHT, null);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == pickTank) {
			this.selectedPlayerType = EntityType.TANK;
			updateCharacterView();
		} else if (event.getSource() == pickHealer) {
			this.selectedPlayerType = EntityType.HEALER;
			updateCharacterView();
		} else if (event.getSource() == pickArcher) {
			this.selectedPlayerType = EntityType.ARCHER;
			updateCharacterView();
		} else if (event.getSource() == pickMage) {
			this.selectedPlayerType = EntityType.MAGE;
			updateCharacterView();
		} else if (event.getSource() == pickRogue) {
			this.selectedPlayerType = EntityType.ROGUE;
			updateCharacterView();
		} else if (event.getSource() == connectButton) {
			try {
				InetAddress address = InetAddress.getByName(serverIpField.getText());

				loadIntoGame(address, selectedPlayerType);
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(this, "That's not a valid server IP.", "Invalid Server IP",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void updateCharacterView() { // can't make abstract methods static, so it's awkward to get the file location from the entity's class
		try {
			switch (selectedPlayerType) {
				case TANK:
					characterView.setIcon(new ImageIcon(ImageIO.read(FileReader.getFileFromResourceString("Tank.png"))
							.getSubimage(0, 0, 32, 48)));
					break;
				case ROGUE:
					characterView.setIcon(new ImageIcon(ImageIO.read(FileReader.getFileFromResourceString("Rogue.png"))
							.getSubimage(0, 0, 32, 48)));
					break;
				case ARCHER:
					characterView.setIcon(new ImageIcon(ImageIO
							.read(FileReader.getFileFromResourceString("Archer.png")).getSubimage(0, 0, 32, 48)));
					break;
				case HEALER:
					characterView.setIcon(new ImageIcon(ImageIO
							.read(FileReader.getFileFromResourceString("Healer.png")).getSubimage(0, 0, 32, 48)));
					break;
				case MAGE:
					characterView.setIcon(new ImageIcon(ImageIO.read(FileReader.getFileFromResourceString("Mage.png"))
							.getSubimage(0, 0, 32, 48)));
					break;
			}
		} catch (IOException e) {

		}
	}

	@Override
	public void setClient(Client client) {
		this.client = client;
	}

}
