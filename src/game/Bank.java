package game;

import java.util.List;

/**
 * Buildings are kept in Bank, so players 
 * take those from this class. 
 */
public class Bank {
	private static int numberOfHouses;
	private static int numberOfHotels;
	private static int numberOfSkyscraper;
	private static int numberOfTraindepots;
	private static int numberOfCapStand;

/**
 * Initializing the total number of each building type. 
 * (e.g. numberOfHouses assigned to 81)
 * 
 */
	public Bank() {
		numberOfHouses=81;
		numberOfHotels=31;
		numberOfSkyscraper=16;
		numberOfTraindepots=4;
		numberOfCapStand=4;
	}

	public static int getNumberOfTraindepots() {
		return numberOfTraindepots;
	}

	public static void setNumberOfTraindepots(int numberOfTraindepots) {
		Bank.numberOfTraindepots = numberOfTraindepots;
	}

	public static int getNumberOfCapStand() {
		return numberOfCapStand;
	}

	public static void setNumberOfCapStand(int numberOfCapStand) {
		Bank.numberOfCapStand = numberOfCapStand;
	}

	public static int getNumberOfHouses() {
		return numberOfHouses;
	}

	public static void setNumberOfHouses(int numberOfHouses) {
		Bank.numberOfHouses = numberOfHouses;
	}

	public static int getNumberOfHotels() {
		return numberOfHotels;
	}

	public static void setNumberOfHotels(int numberOfHotels) {
		Bank.numberOfHotels = numberOfHotels;
	}

	public static int getNumberOfSkyscraper() {
		return numberOfSkyscraper;
	}

	public static void setNumberOfSkyscraper(int numberOfSkyscraper) {
		Bank.numberOfSkyscraper = numberOfSkyscraper;
	}
	public static int getMax(List<Integer> a){ 
		int max = a.get(0); 
		for (int i = 0; i < a.size(); i++) { 
			if (a.get(i) > max) {
				max = a.get(i);
			}
		} 
		return max;
	}
}