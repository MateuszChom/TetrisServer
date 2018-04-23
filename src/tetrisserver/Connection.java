package tetrisserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Connection extends Thread {

	private ServerSocket _serverSocket;
	private int _port;
	private boolean _online;

	public Connection(int port) {
		_port = port;
		_online = true;
		start();
	}
	
	public void run() {
		try {
			_serverSocket = new ServerSocket(_port);
			Panel.panel.addMessage(Config.language[9] + " " + _port,
					Config.INFO, true);
		} catch (IOException e) {
			Panel.panel.addMessage(Config.language[3] + " " + _port + " "
					+ Config.language[10], Config.ERROR, true);
			Panel.panel.setEnabled();
			return;
		} catch (IllegalArgumentException e) {
			Panel.panel.addMessage(Config.language[16], Config.ERROR, true);
			Panel.panel.setEnabled();
			return;
		}

		while (_online) {
			try {
				new Data(_serverSocket.accept());
			} catch (SocketException e) {
			} catch (IOException e) {
				Panel.panel.addMessage(Config.language[11], Config.ERROR, true);
			}
		}
	}
	
	public void interrupt() {
		try {
			_online = false;
			_serverSocket.close();
		} catch (IOException e) {
		}
	}
}