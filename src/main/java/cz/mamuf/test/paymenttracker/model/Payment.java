package cz.mamuf.test.paymenttracker.model;

/**
 * Payment record.
 */
public class Payment {

	private final Currency currency;
	private final int value;

	public Payment(final Currency currency, final int value) {
		this.currency = currency;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public Currency getCurrency() {
		return currency;
	}

}
