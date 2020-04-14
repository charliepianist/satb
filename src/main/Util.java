package main;

import java.util.Arrays;

public class Util {
	public static String repeatChar(char c, int n) {
		char[] repeat = new char[n];
		Arrays.fill(repeat, c);
		return new String(repeat);
	}
}
