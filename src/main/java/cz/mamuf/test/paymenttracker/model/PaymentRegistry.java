package cz.mamuf.test.paymenttracker.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * {@link Payment} registry. Provides operations for adding payments,
 * statistics. This class is thread-safe.
 */
public class PaymentRegistry {

	private final List<PaymentRegistryListener> listeners = new ArrayList<>();
	private final List<Payment> payments = new ArrayList<>();

	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private final Lock readLock = rwLock.readLock();
	private final Lock writeLock = rwLock.writeLock();

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
		writeLock.lock();
		try {
			payments.addAll(checkNotNull(initialPayments, "initialPayments"));
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Registers a {@link PaymentRegistryListener}.
	 */
	public void addListener(final PaymentRegistryListener listener) {
		writeLock.lock();
		try {
			listeners.add(listener);
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Adds a {@link Payment}.
	 */
	public void add(final Payment payment) {
		writeLock.lock();
		try {
			payments.add(payment);
			listeners.forEach(l -> l.paymentAdded(payment));
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Calculates total for payments with given {@link Currency}.
	 *
	 * @return Sum total of selected payments.
	 */
	public int getTotalByCurrency(final Currency currency) {
		readLock.lock();
		try {
			return payments.stream()
					.filter(p -> p.getCurrency().equals(currency))
					.mapToInt(Payment::getValue)
					.sum();
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Calculates totals for payments grouped by their currencies.
	 *
	 * @return Totals mapped by {@link Currency}.
	 */
	public Map<Currency, Integer> getTotalsByCurrencies() {
		readLock.lock();
		try {
			return payments.stream()
					.collect(groupingBy(
							Payment::getCurrency,
							TreeMap::new,
							summingInt(Payment::getValue)));
		} finally {
			readLock.unlock();
		}
	}

}
