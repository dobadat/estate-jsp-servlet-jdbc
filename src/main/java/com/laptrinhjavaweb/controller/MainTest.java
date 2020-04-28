package com.laptrinhjavaweb.controller;

import java.util.Arrays;

public class MainTest {
	public static void main(String[] args) {
		String[] testArray = { "Apple", "Banana" };
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < testArray.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			String item = testArray[i];
			sb.append(item);
		}
		System.out.println(sb);
	}
}
