package tetrisserver;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")

public class Server extends JFrame {
	public Server() {
		super(Config.language[0]);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			ImageIcon server = new ImageIcon(("icon.png"));
			setIconImage(server.getImage());
		} catch (NullPointerException e) {
		}
		Panel panel = new Panel();
		setPreferredSize(new Dimension(Config.frameDimension[0],
				Config.frameDimension[1]));
		add(panel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Server server = new Server();
				server.setVisible(true);
				server.pack();
				Dimension dm = server.getToolkit().getScreenSize();
				server.setLocation(
						(int) (dm.getWidth() / 8 - server.getWidth() / 8),
						(int) (dm.getHeight() / 4 - server.getHeight() / 4));
			}
		});
	}
}