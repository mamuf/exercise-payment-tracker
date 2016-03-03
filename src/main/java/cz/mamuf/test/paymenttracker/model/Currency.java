package cz.mamuf.test.paymenttracker.model;

/**
 * {@link Payment} currency.
 */
public class Currency {

	private final String currency;

	public Currency(final String currency) {
		validate(currency);
		this.currency = currency;
	}

	private void validate(final String input) {
		// validate using Java 8 Stream and Lambda
		if (input == null
				|| input.length() != 3
				|| input.chars().anyMatch(c -> c < 'A' || c > 'Z')) {
			throw new IllegalArgumentException(input);
		}
	}

	public String getCurrency() {
		return currency;
	}

}
