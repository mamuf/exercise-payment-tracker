package cz.mamuf.test.paymenttracker;

import java.io.File;

/**
 * Main class.
 */
public class Main {
	public static void main(final String[] args) {
		String filename = null;
		if (args.length >= 1) {
			filename = args[0];
		}

		try {
			final PaymentTracker paymentTracker = filename != null
					? new PaymentTracker(new PaymentStore(new File(filename)))
					: new PaymentTracker();
			paymentTracker.run();
		} catch (final RuntimeException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
}
