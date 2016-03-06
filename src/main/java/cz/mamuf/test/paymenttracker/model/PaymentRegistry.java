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

	public static interface PaymentRegistryListener {
		void paymentAdded(Payment payment);
	}

	public void addListener(final PaymentRegistryListener listener) {
		listeners.add(listener);
	}

	public void add(final Payment payment) {
		payments.add(payment);
		listeners.forEach(l -> l.paymentAdded(payment));
	}

	public int getTotalByCurrency(final Currency currency) {
		return payments.stream()
				.filter(p -> p.getCurrency().equals(currency))
				.mapToInt(Payment::getValue)
				.sum();
	}

	public Map<Currency, Integer> getTotalsByCurrencies() {
		// map by Currency and merge the values by adding them up, effectively
		// calculating the totals
		return payments.stream().collect(toMap(
				Payment::getCurrency,
				Payment::getValue,
				(a, b) -> a + b,
				TreeMap::new));
	}

}
