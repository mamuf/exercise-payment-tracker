package cz.mamuf.test.paymenttracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cz.mamuf.test.paymenttracker.model.Payment;

/**
 * DAO for {@link Payment}s. Loads and stores payments in a {@link File}.
 */
public class PaymentStore {

	private final File file;

	public PaymentStore(final File file) {
		this.file = file;
	}

	/**
	 * Loads all payments from the file. If the file does not exist, returns an
	 * empty {@link Collection}.
	 *
	 * @return Collection of {@link Payment}s.
	 * @throws IOException When file could not be read.
	 * @throws RuntimeException When the file format is invalid.
	 */
	public Collection<Payment> loadAll() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			final List<Payment> result = new ArrayList<>();
			while (reader.ready()) {
				final String line = reader.readLine();
				result.add(parsePayment(line));
			}
			return result;
		} catch (final FileNotFoundException e) {
			return Collections.emptyList();
		}
	}

	private Payment parsePayment(final String line) {
		try {
			return Payment.parsePayment(line);
		} catch (final IllegalArgumentException e) {
			throw new RuntimeException("Invalid file format, could not parse line: " + e.getMessage(), e);
		}
	}

	/**
	 * Appends new {@link Payment} record to the file.
	 *
	 * @throws IOException
	 */
	public void add(final Payment payment) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			writer.write(formatPayment(payment));
			writer.newLine();
		}
	}

	private String formatPayment(final Payment payment) {
		return payment.getCurrency().getCurrency() + " " + payment.getValue();
	}

}
