package cz.mamuf.test.paymenttracker.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.Collection;
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
	 * Creates emtpy registry.
	 */
	public PaymentRegistry() {
	}

	/**
	 * Creates registry with initial collection of {@link Payment}s.
	 *
	 * @param initialPayments
	 */
	public PaymentRegistry(final Collection<Payment> initialPayments) {
		payments.addAll(checkNotNull(initialPayments, "initialPayments"));
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
		return payments.stream()
				.collect(groupingBy(
						Payment::getCurrency,
						TreeMap::new,
						summingInt(Payment::getValue)));
	}

}
