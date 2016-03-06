package cz.mamuf.test.paymenttracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cz.mamuf.test.paymenttracker.model.Currency;
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
	 * Loads all payments from the file.
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
		}
	}

	private Payment parsePayment(final String line) {
		try {
			final int sepIdx = line.indexOf(' ');
			final String currencySting = line.substring(0, sepIdx);
			final String valueString = line.substring(sepIdx + 1);
			return new Payment(new Currency(currencySting), Integer.parseInt(valueString));
		} catch (final RuntimeException e) {
			throw new RuntimeException("Invalid file format, could not parse line '" + line + "': "
					+ e.getMessage(), e);
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
