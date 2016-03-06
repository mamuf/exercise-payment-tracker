package cz.mamuf.test.paymenttracker;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import cz.mamuf.test.paymenttracker.model.Payment;
import cz.mamuf.test.paymenttracker.model.PaymentRegistry;
import cz.mamuf.test.paymenttracker.model.PaymentRegistry.PaymentRegistryListener;

/**
 * Payment Tracker controller.
 */
public class PaymentTracker {

	private final PaymentRegistry model;
	private final ConsoleUI ui;
	private final Optional<PaymentStore> optionalStore;

	/**
	 * Creates the application controller.
	 */
	public PaymentTracker() {
		this(Optional.empty());
	}

	/**
	 * Same as {@link #PaymentTracker(ConsoleUI)} but uses a
	 * {@link PaymentStore} to load initial payments and append new ones.
	 *
	 * @param ui Must not be <code>null</code>.
	 * @param optionalStore Must not be <code>null</code>.
	 */
	public PaymentTracker(final PaymentStore paymentStore) {
		this(Optional.of(checkNotNull(paymentStore, "paymentStore")));
	}

	private PaymentTracker(final Optional<PaymentStore> optionalStore) {
		this.optionalStore = optionalStore;

		try {
			model = createModel();
			ui = new ConsoleUI(model);
		} catch (final IOException e) {
			throw new PaymentTrackerException("Could not initialize: " + e.getMessage(), e);
		}
	}

	private PaymentRegistry createModel() throws IOException {
		final PaymentRegistry result = new PaymentRegistry(loadAllPayments());
		result.addListener(new PaymentRegistryListener() {
			@Override
			public void paymentAdded(final Payment payment) {
				handlePaymentAdded(payment);
			}
		});
		return result;
	}

	private Collection<Payment> loadAllPayments() throws IOException {
		return optionalStore.isPresent()
				? optionalStore.get().loadAll()
				: Collections.emptyList();
	}

	private void handlePaymentAdded(final Payment payment) {
		optionalStore.ifPresent(s -> {
			try {
				s.add(payment);
			} catch (final IOException e) {
				throw new PaymentTrackerException("Could not add payment to file: " + e.getMessage(), e);
			}
		});
	}

	public PaymentRegistry getModel() {
		return model;
	}

}
