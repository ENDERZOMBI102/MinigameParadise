package com.enderzombi102.MinigameParadise;

import java.util.List;

public class Random {

	private static java.util.Random random =  new java.util.Random();

	@SuppressWarnings("IntegerDivisionInFloatContext")
	public static boolean possibility(int percent) {
		return random.nextFloat() <= percent / 100F;
	}

	public static boolean possibility(float percent) {
		return random.nextFloat() <= percent;
	}

	public static int randomInt(int max, int min) {
		int x;
		do {
			x = random.nextInt(max);
		} while(x < min);
		return x;
	}

	public static int randomInt(int max) {
		return random.nextInt(max);
	}

	public static <E> E randomEntry(List<E> list) {
		return list.get( random.nextInt( list.size() - 1) );
	}

	public static void regenerateRandomizer() {
		random = new java.util.Random();
	}

}
