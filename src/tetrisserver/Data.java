package tetrisserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Data extends Thread {

	private Socket _socket;
	private BufferedReader _br;
	private PrintWriter _pw;

	Data(Socket socket) throws IOException {

		_socket = socket;
		InputStream is = socket.getInputStream();
		_br = new BufferedReader(new InputStreamReader(is));
		OutputStream os = socket.getOutputStream();
		_pw = new PrintWriter(os, true);
		start();
	}

	public void run() {

		try {
			String command = _br.readLine();
			Panel.panel.addMessage(Config.language[14] + " "
					+ _socket.getInetAddress().getHostAddress() + ": "
					+ command, Config.RECEIVED, true);

			switch (command) {

			case Protocol.CHECK: {
				_pw.println(Protocol.CHECKED);
				Panel.panel.addMessage(Config.language[13] + " "
						+ _socket.getInetAddress().getHostAddress() + ": "
						+ Protocol.CHECKED, Config.SENT, true);
				_socket.close();
				break;
			}

			case Protocol.GETTETRIMINOCOLORS: {
				String[] answer = TetrisConfig.setColor("#Tetrimino Color", 9);
				if (answer == null) {
					_pw.println(Protocol.ERROR);
					Panel.panel.addMessage(Config.language[15], Config.ERROR,
							true);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ Protocol.ERROR, Config.SENT, true);
					_socket.close();
					break;
				}
				Panel.panel.addMessage(Config.language[17], Config.INFO, true);
				for (int i = 0; i < 9; i++) {
					_pw.println(answer[i]);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress()
							+ ": 0x" + answer[i], Config.SENT, true);
				}
				_socket.close();
				break;
			}

			case Protocol.GETINTERFACECOLORS: {
				String[] answer = TetrisConfig.setColor("#Interface Color", 4);
				if (answer == null) {
					_pw.println(Protocol.ERROR);
					Panel.panel.addMessage(Config.language[18], Config.ERROR,
							true);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ Protocol.ERROR, Config.SENT, true);
					_socket.close();
					break;
				}
				Panel.panel.addMessage(Config.language[19], Config.INFO, true);
				for (int i = 0; i < 4; i++) {
					_pw.println(answer[i]);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress()
							+ ": 0x" + answer[i], Config.SENT, true);
				}
				_socket.close();
				break;
			}

			case Protocol.GETTEXTSIZE: {
				String[] answer = TetrisConfig.setInt("#Text Size", 3);
				if (answer == null) {
					_pw.println(Protocol.ERROR);
					Panel.panel.addMessage(Config.language[20], Config.ERROR,
							true);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ Protocol.ERROR, Config.SENT, true);
					_socket.close();
					break;
				}
				Panel.panel.addMessage(Config.language[21], Config.INFO, true);
				for (int i = 0; i < 3; i++) {
					_pw.println(answer[i]);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ answer[i], Config.SENT, true);
				}
				_socket.close();
				break;
			}

			case Protocol.GETBOARDDIMENSION: {
				String[] answer = TetrisConfig.setInt("#Board Dimension", 2);
				if (answer == null) {
					_pw.println(Protocol.ERROR);
					Panel.panel.addMessage(Config.language[22], Config.ERROR,
							true);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ Protocol.ERROR, Config.SENT, true);
					_socket.close();
					break;
				}
				Panel.panel.addMessage(Config.language[23], Config.INFO, true);
				for (int i = 0; i < 2; i++) {
					_pw.println(answer[i]);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ answer[i], Config.SENT, true);
				}
				_socket.close();
				break;
			}

			case Protocol.GETBOARDSIZE: {
				String answer = TetrisConfig.fromFile("#Board Size");
				if (answer == null) {
					_pw.println(Protocol.ERROR);
					Panel.panel.addMessage(Config.language[36], Config.ERROR,
							true);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ Protocol.ERROR, Config.SENT, true);
					_socket.close();
					break;
				}
				Panel.panel.addMessage(Config.language[37], Config.INFO, true);
				_pw.println(answer);
				Panel.panel.addMessage(Config.language[13] + " "
						+ _socket.getInetAddress().getHostAddress() + ": "
						+ answer, Config.SENT, true);
				_socket.close();
				break;
			}

			case Protocol.SENDINGSCORE: {
				Panel.panel.addMessage(Config.language[26], Config.INFO, true);
				try {
					_socket.setSoTimeout(Config.timeout);
					String name = _br.readLine();
					Panel.panel.addMessage(Config.language[14] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ name, Config.RECEIVED, true);
					String score = _br.readLine();
					Panel.panel.addMessage(Config.language[14] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ score, Config.RECEIVED, true);
					_socket.setSoTimeout(0);
					TetrisConfig.saveScore(name, score);
				} catch (SocketTimeoutException e) {
					_socket.setSoTimeout(0);
					Panel.panel.addMessage(Config.language[25], Config.ERROR,
							true);
				}
				_socket.close();
				break;
			}

			case Protocol.GETHIGHSCORES: {
				String[] answer = TetrisConfig.getHighScores();
				if (answer == null) {
					_pw.println(Protocol.ERROR);
					Panel.panel.addMessage(Config.language[27], Config.ERROR,
							true);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ Protocol.ERROR, Config.SENT, true);
					_socket.close();
					break;
				}
				Panel.panel.addMessage(Config.language[31], Config.INFO, true);
				for (int i = 0; i < 20; i++) {
					_pw.println(answer[i]);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ answer[i], Config.SENT, true);
				}
				_socket.close();
				break;
			}

			case Protocol.GETLEVEL: {
				String[] answer = null;
				Panel.panel.addMessage(Config.language[32], Config.INFO, true);
				try {
					_socket.setSoTimeout(Config.timeout);
					String number = _br.readLine();
					Panel.panel.addMessage(Config.language[14] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ number, Config.RECEIVED, true);
					String height = _br.readLine();
					Panel.panel.addMessage(Config.language[14] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ height, Config.RECEIVED, true);
					String width = _br.readLine();
					Panel.panel.addMessage(Config.language[14] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ width, Config.RECEIVED, true);
					_socket.setSoTimeout(0);
					answer = TetrisConfig.getLevel(number, height, width);
				} catch (SocketTimeoutException e) {
					_socket.setSoTimeout(0);
					Panel.panel.addMessage(Config.language[25], Config.ERROR,
							true);
				}
				if (answer == null) {
					_pw.println(Protocol.ERROR);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ Protocol.ERROR, Config.SENT, true);
					_socket.close();
					break;
				}
				Panel.panel.addMessage(Config.language[35], Config.INFO, true);
				for (int i = 0; i < answer.length; i++) {
					_pw.println(answer[i]);
					Panel.panel.addMessage(Config.language[13] + " "
							+ _socket.getInetAddress().getHostAddress() + ": "
							+ answer[i], Config.SENT, true);
				}
				_socket.close();
				break;
			}

			default:
				_pw.println(Protocol.ERROR);
				Panel.panel.addMessage(Config.language[12], Config.ERROR, true);
				Panel.panel.addMessage(Config.language[13] + " "
						+ _socket.getInetAddress().getHostAddress() + ": "
						+ Protocol.ERROR, Config.SENT, true);
				_socket.close();
				break;
			}
			_socket.close();

		} catch (IOException e) {
			Panel.panel.addMessage(Config.language[24], Config.ERROR, true);
		}
	}
}