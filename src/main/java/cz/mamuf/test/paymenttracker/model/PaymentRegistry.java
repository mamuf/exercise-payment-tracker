package cz.mamuf.test.paymenttracker.model;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * {@link Payment} registry. Provides operations for adding payments,
 * statistics.
 */
public class PaymentRegistry {

	private final List<PaymentRegistryListener> listeners = new ArrayList<>();
	private final List<Payment> payments = new ArrayList<>();

	/**
	 * {@link PaymentRegistry} listener. Implementors should not throw any
	 * exceptions.
	 */
	public static interface PaymentRegistryListener {
		/**
		 * Executed at the end of {@link PaymentRegistry#add(Payment)} method.
		 */
		void paymentAdded(Payment payment);
	}

	/**
	 * Registers a {@link PaymentRegistryListener}.
	 */
	public void addListener(final PaymentRegistryListener listener) {
		listeners.add(listener);
	}

	/**
	 * Adds a {@link Payment}.
	 */
	public void add(final Payment payment) {
		payments.add(payment);
		listeners.forEach(l -> l.paymentAdded(payment));
	}

	/**
	 * Calculates total for payments with given {@link Currency}.
	 *
	 * @return Sum total of selected payments.
	 */
	public int getTotalByCurrency(final Currency currency) {
		return payments.stream()
				.filter(p -> p.getCurrency().equals(currency))
				.mapToInt(Payment::getValue)
				.sum();
	}

	/**
	 * Calculates totals for payments grouped by their currencies.
	 *
	 * @return Totals mapped by {@link Currency}.
	 */
	public Map<Currency, Integer> getTotalsByCurrencies() {
		/*
		 * map values by Currency and merge the values by adding them up,
		 * effectively calculating the totals
		 */
		return payments.stream().collect(toMap(
				Payment::getCurrency,
				Payment::getValue,
				(a, b) -> a + b,
				TreeMap::new));
	}

}
