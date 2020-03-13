package rg.vaadin.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioCliente {

	private static ServicioCliente instance;
	private static final Logger LOGGER = Logger.getLogger(ServicioCliente.class.getName());

	private final HashMap<Long, Cliente> contacts = new HashMap<>();
	private long nextId = 0;

	private ServicioCliente() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static ServicioCliente getInstance() {
		if (instance == null) {
			instance = new ServicioCliente();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Customer objects.
	 */
	public synchronized List<Cliente> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized List<Cliente> findAll(String stringFilter) {
		ArrayList<Cliente> arrayList = new ArrayList<>();
		for (Cliente contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Cliente>() {

			@Override
			public int compare(Cliente o1, Cliente o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	/**
	 * Finds all Customer's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Customer objects
	 */
	public synchronized List<Cliente> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Cliente> arrayList = new ArrayList<>();
		for (Cliente contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Cliente>() {

			@Override
			public int compare(Cliente o1, Cliente o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	/**
	 * @return the amount of all customers in the system
	 */
	public synchronized long count() {
		return contacts.size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(Cliente value) {
		contacts.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Cliente entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Cliente) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] names = new String[] { "Carlos Ortiz", "Esteban Bustos", "Rodrigo Aguayo","Omar Romero", "Jose Collio",
					"Emilia Guajardo"};
			Random r = new Random(0);
			for (String name : names) {
				String[] split = name.split(" ");
				Cliente c = new Cliente();
				c.setNombre(split[0]);
				c.setApellido(split[1]);
				c.setStatus(EstadoCliente.values()[r.nextInt(EstadoCliente.values().length)]);
				c.setBirthDate(LocalDate.now().minusDays(r.nextInt(365*100)));
				save(c);
			}
		}
	}
}