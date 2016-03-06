package cz.mamuf.test.paymenttracker;

public class PaymentTrackerException extends RuntimeException {

	public PaymentTrackerException() {
		super();
	}

	public PaymentTrackerException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PaymentTrackerException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public PaymentTrackerException(final String message) {
		super(message);
	}

	public PaymentTrackerException(final Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}
