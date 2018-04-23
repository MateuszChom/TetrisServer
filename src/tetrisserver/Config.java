package tetrisserver;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Config {

	public static int[] frameDimension;
	public static int[] buttonDimension;
	public static int[] textFieldDimension;
	public static Color[] fontColor;
	public static int[] fontSize;
	public static int port;
	public static int timeout;
	public static String[] language;

	public static int INFO = 0;
	public static int SENT = 1;
	public static int RECEIVED = 2;
	public static int ERROR = 3;

	private static int[] _defaultFrameDimension = { 600, 700 };
	private static int[] _defaultbuttonDimension = { 80, 25 };
	private static int[] _defaultTextFieldDimension = { 7, 25 };
	private static Color[] _defaultfontColor = { new Color(0x0044AA),
			new Color(0x006600), new Color(0x8B008B), new Color(0xEE1111),
			new Color(0x0055BB) };
	private static int[] _defaultFontSize = { 12, 12 };
	private static int _defaultPort = 1000;
	private static int _defaultTimeout = 3000;
	private static String[] _defaultLanguage = { "Tetris - Server", "Start",
			"Exit", "Port", "Server IP", "Cannot fix the IP number", "Unknown",
			"Incorrect port number", "Stop", "Server is ready to work on port",
			"is already in use", "Server error", "Unknown command", "To",
			"From", "Cannot read tetrimino colors from config file",
			"Port number is too long", "Sending tetrimino colors",
			"Cannot read interface colors from config file",
			"Sending interface colors",
			"Cannot read text size from config file", "Sending text size",
			"Cannot read board dimension from config file",
			"Sending board dimension", "Input/output exception",
			"Socket timeout", "Waiting for name and score",
			"Cannot read high scores from file",
			"Data sent by client is incorrect",
			"Cannot save high scores to file",
			"New high score have been saved to the file",
			"Sending high scores", "Waiting for level number and parameters",
			"This level does not exist",
			"Cannot read level parameters from file",
			"Sending level parameters",
			"Cannot read board size from config file", "Sending board size" };

	static {
		frameDimension = setInt("#Frame Dimension", 2, _defaultFrameDimension);
		buttonDimension = setInt("#Button Dimension", 2,
				_defaultbuttonDimension);
		textFieldDimension = setInt("#TextField Dimension", 2,
				_defaultTextFieldDimension);
		fontColor = setColor("#Font Color", 5, _defaultfontColor);
		fontSize = setInt("#Font Size", 2, _defaultFontSize);
		language = setLanguage();
		port = setValue("#Default Port", _defaultPort);
		timeout = setValue("#Socket Timeout", _defaultTimeout);
	}

	private static int setValue(String parameter, int defaultValue) {
		String temp = fromFile(parameter);
		int tempValue;
		if (temp == null)
			return defaultValue;
		try {
			tempValue = Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
		return tempValue;
	}

	private static int[] setInt(String parameter, int count, int[] defaultInt) {
		String temp = fromFile(parameter);
		if (temp == null)
			return defaultInt;
		try {
			int[] space = new int[count - 1];
			space[0] = temp.indexOf(' ');
			for (int i = 0; i < count - 2; i++)
				space[i + 1] = temp.indexOf(' ', space[i] + 1);
			int length = temp.length();
			String[] tempString = new String[count];
			tempString[0] = temp.substring(0, space[0]);
			for (int i = 1; i < count - 1; i++)
				tempString[i] = temp.substring(space[i - 1] + 1, space[i]);
			tempString[count - 1] = temp
					.substring(space[count - 2] + 1, length);
			int[] tempInt = new int[count];
			for (int i = 0; i < count; i++)
				tempInt[i] = Integer.parseInt(tempString[i]);
			return tempInt;
		} catch (Exception e) {
			return defaultInt;
		}
	}

	private static Color[] setColor(String parameter, int count,
			Color[] defaultColor) {
		String temp = fromFile(parameter);
		if (temp == null)
			return defaultColor;
		try {
			int[] space = new int[count - 1];
			space[0] = temp.indexOf(' ');
			for (int i = 0; i < count - 2; i++)
				space[i + 1] = temp.indexOf(' ', space[i] + 1);
			int length = temp.length();
			String[] tempColor = new String[count];
			tempColor[0] = temp.substring(2, space[0]);
			for (int i = 1; i < count - 1; i++)
				tempColor[i] = temp.substring(space[i - 1] + 3, space[i]);
			tempColor[count - 1] = temp.substring(space[count - 2] + 3, length);
			Color[] color = new Color[count];
			for (int i = 0; i < count; i++)
				color[i] = new Color(Integer.parseInt(tempColor[i], 16));
			return color;
		} catch (Exception e) {
			return defaultColor;
		}
	}

	private static String[] setLanguage() {
		File file = new File("language.txt");
		Scanner in = null;
		int count = 38;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			return _defaultLanguage;
		}
		String[] temp = new String[count];
		try {
			in.nextLine();
			for (int i = 0; i < count; i++) {
				temp[i] = in.nextLine();
			}
		} catch (NoSuchElementException e) {
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return _defaultLanguage;
		} catch (IllegalStateException e) {
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return _defaultLanguage;
		}
		try {
			in.close();
		} catch (IllegalStateException ise) {
		}
		return temp;
	}

	private static String fromFile(String description) {
		File file = new File("config.txt");
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			return null;
		}
		String parameter = null;
		try {
			while (true) {
				parameter = in.nextLine();
				if (parameter.equals(description)) {
					parameter = in.nextLine();
					break;
				}
			}
		} catch (NoSuchElementException e) {
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return null;
		} catch (IllegalStateException e) {
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return null;
		}
		try {
			in.close();
		} catch (IllegalStateException ise) {
		}
		return parameter;
	}
}