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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Currency other = (Currency) obj;
		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		return true;
	}

}
