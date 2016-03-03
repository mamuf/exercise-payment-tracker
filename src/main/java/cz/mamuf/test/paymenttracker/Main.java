package cz.mamuf.test.paymenttracker;

import java.util.Scanner;

/**
 * Main class.
 */
public class Main {
	public static void main(final String[] args) {
		System.out.println("Payment Tracker");

		final Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\s*");
		while (!sc.hasNext("z")) {
			final char ch = sc.next().charAt(0);
			System.out.print("[" + ch + "] ");
		}
		sc.close();
	}
}
