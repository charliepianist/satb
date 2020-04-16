package com.charliepianist.main;

import java.util.Arrays;

public class Util {
	public static String repeatChar(char c, int n) {
		char[] repeat = new char[n];
		Arrays.fill(repeat, c);
		return new String(repeat);
	}
	
	public static class Range {
		private int min, max;

		public static final Range R13 = new Range(1, 3);
		public static final Range R12 = new Range(1, 2);
		public static final Range R11 = new Range(1, 1);
		public static final Range R02 = new Range(0, 2);
		public static final Range R01 = new Range(0, 1);
		
		public Range(int min, int max) {
			this.min = min;
			this.max = max;
		}
		
		public boolean contains(int i) {
			return i <= max && i >= min;
		}
		
		public int getMin() {
			return min;
		}
		
		public int getMax() {
			return max;
		}
	}
}
