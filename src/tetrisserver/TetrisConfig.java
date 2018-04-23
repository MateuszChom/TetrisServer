package tetrisserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TetrisConfig {

	public static String[] getLevel(String number, String height, String width) {
		File file = new File("Tetris/Levels/" + height + "x" + width + "_level"
				+ number + ".txt");
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			Panel.panel.addMessage(Config.language[33], Config.ERROR, true);
			return null;
		}
		int h = Integer.parseInt(height);
		String[] temp = new String[h + 3];
		String parameters;
		try {
			in.nextLine();
			parameters = in.nextLine();
			in.nextLine();
			in.nextLine();
			for (int i = 0; i < h; i++) {
				temp[i + 3] = in.nextLine();
			}
		} catch (NoSuchElementException e) {
			Panel.panel.addMessage(Config.language[34], Config.ERROR, true);
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return null;
		} catch (IllegalStateException e) {
			Panel.panel.addMessage(Config.language[34], Config.ERROR, true);
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
		try {
			int space1 = parameters.indexOf(' ');
			int space2 = parameters.indexOf(' ', space1 + 1);
			int length = parameters.length();
			temp[0] = parameters.substring(0, space1);
			temp[1] = parameters.substring(space1 + 1, space2);
			temp[2] = parameters.substring(space2 + 1, length);
		} catch (Exception e) {
			Panel.panel.addMessage(Config.language[34], Config.ERROR, true);
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return null;
		}
		return temp;
	}
	
	public static String[] getHighScores() {
		File file = new File("Tetris/high_scores.txt");
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			return null;
		}
		String[] temp = new String[20];
		try {
			for (int i = 0; i < 20; i++) {
				temp[i] = in.nextLine();
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
		} catch (IllegalStateException e) {
		}
		return temp;
	}
	
	public static void saveScore(String name, String score) {
		File file = new File("Tetris/high_scores.txt");
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			Panel.panel.addMessage(Config.language[27], Config.ERROR, true);
			return;
		}
		String[] names = new String[10];
		String[] scores = new String[10];
		try {
			for (int i = 0; i < 10; i++) {
				names[i] = in.nextLine();
				scores[i] = in.nextLine();
			}
		} catch (NoSuchElementException e) {
			Panel.panel.addMessage(Config.language[27], Config.ERROR, true);
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return;
		} catch (IllegalStateException e) {
			Panel.panel.addMessage(Config.language[27], Config.ERROR, true);
			try {
				in.close();
			} catch (IllegalStateException ise) {
			}
			return;
		}
		try {
			in.close();
		} catch (IllegalStateException e) {
		}
		try {
			int high = Integer.parseInt(score);
			for (int i = 0; i < 10; i++) {
				if (Integer.parseInt(scores[i]) < high) {
					for (int j = 9; j > i; j--) {
						scores[j] = scores[j - 1];
						names[j] = names[j - 1];
					}
					scores[i] = score;
					names[i] = name;
					break;
				}
				if (i == 9) {
					return;
				}
			}
		} catch (NumberFormatException e) {
			Panel.panel.addMessage(Config.language[28], Config.ERROR, true);
			return;
		}
		PrintWriter save = null;
		try {
			save = new PrintWriter("Tetris/high_scores.txt");
			for (int i = 0; i < 10; i++) {
				save.println(names[i]);
				save.println(scores[i]);
			}
			save.close();
			Panel.panel.addMessage(Config.language[30], Config.INFO, true);
		} catch (FileNotFoundException e) {
			Panel.panel.addMessage(Config.language[29], Config.ERROR, true);
		} catch (IllegalStateException e) {
			Panel.panel.addMessage(Config.language[29], Config.ERROR, true);
			try {
				save.close();
			} catch (IllegalStateException ise) {
			}
		}
	}

	public static String[] setInt(String parameter, int count) {
		String temp = fromFile(parameter);
		if (temp == null)
			return null;
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
			return tempString;
		} catch (Exception e) {
			return null;
		}
	}

	public static String[] setColor(String parameter, int count) {
		String temp = fromFile(parameter);
		if (temp == null)
			return null;
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
			return tempColor;
		} catch (Exception e) {
			return null;
		}
	}

	public static String fromFile(String description) {
		File file = new File("Tetris/config.txt");
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
