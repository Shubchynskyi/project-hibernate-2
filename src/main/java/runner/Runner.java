package runner;

import dao.*;
import entity.address.Address;
import entity.address.City;
import entity.film.*;
import entity.store.*;
import jakarta.persistence.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.reflections.Reflections;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Set;

public class Runner {

    private final SessionFactory sessionFactory;

    private final AddressDao addressDao;
    private final CityDao cityDao;
    private final CountryDao countryDao;
    private final ActorDao actorDao;
    private final CategoryDao categoryDao;
    private final FeatureDao featureDao;
    private final FilmDao filmDao;
    private final FilmTextDao filmTextDao;
    private final LanguageDao languageDao;
    private final CustomerDao customerDao;
    private final InventoryDao inventoryDao;
    private final PaymentDao paymentDao;
    private final RentalDao rentalDao;
    private final StaffDao staffDao;
    private final StoreDao storeDao;

    public Runner() {
        Configuration configuration = new Configuration();

        String packageName = "entity";
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        for (Class<?> entityClass : entityClasses) {
            configuration.addAnnotatedClass(entityClass);
        }

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        sessionFactory = configuration.buildSessionFactory();
        System.out.println(sessionFactory);


        addressDao = new AddressDao(sessionFactory);
        cityDao = new CityDao(sessionFactory);
        countryDao = new CountryDao(sessionFactory);
        actorDao = new ActorDao(sessionFactory);
        categoryDao = new CategoryDao(sessionFactory);
        featureDao = new FeatureDao(sessionFactory);
        filmDao = new FilmDao(sessionFactory);
        filmTextDao = new FilmTextDao(sessionFactory);
        languageDao = new LanguageDao(sessionFactory);
        customerDao = new CustomerDao(sessionFactory);
        inventoryDao = new InventoryDao(sessionFactory);
        paymentDao = new PaymentDao(sessionFactory);
        rentalDao = new RentalDao(sessionFactory);
        staffDao = new StaffDao(sessionFactory);
        storeDao = new StoreDao(sessionFactory);
    }


    public static void main(String[] args) {
        Runner runner = new Runner();
        Customer customer = runner.createCustomer();
        runner.newFilmWasMade();
        runner.customerRentInventory(customer);
        runner.customerReturnInventoryToStore();

    }

    private void newFilmWasMade() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Language language = languageDao.getItems(0, 5).stream().unordered().findAny().orElseGet(null);

                List<Category> categories = categoryDao.getItems(0, 5);
                List<Actor> actors = actorDao.getItems(0, 10);
                Film film = new Film();
                film.setActors(actors);
                film.setRating(Rating.PG13);
                film.setSpecialFeatures(Set.of(Feature.COMMENTARIES, Feature.BEHIND_THE_SCENES));
                film.setLength((short) 120);
                film.setReplacementCost(BigDecimal.valueOf(20));
                film.setRentalRate(BigDecimal.ZERO);
                film.setLanguage(language);
                film.setDescription("Test Description");
                film.setTitle("Test Title");
                film.setRentalDuration((byte) 3);
                film.setOriginalLanguage(language);
                film.setCategories(categories);
                film.setReleaseYear(Year.now());
                filmDao.save(film);

                FilmText filmText = new FilmText();
                filmText.setFilm(film);
                filmText.setDescription("dscrptn");
                filmText.setTitle("ttl");
                filmText.setId(film.getId());
                filmTextDao.save(filmText);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        }

    }

    private void customerRentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                Film film = filmDao.getFirstAvailableFilmForRent();
                Store store = storeDao.getItems(0, 1).get(0);
                Inventory inventory = new Inventory();
                inventory.setFilm(film);
                inventory.setStore(store);
                inventoryDao.save(inventory);

                Staff staff = store.getStaff();

                Rental rental = new Rental();
                rental.setRentalDate(LocalDateTime.now());
                rental.setCustomer(customer);
                rental.setInventory(inventory);
                rental.setStaff(staff);
                rentalDao.save(rental);

                Payment payment = new Payment();
                payment.setRental(rental);
                payment.setPaymentDate(LocalDateTime.now());
                payment.setCustomer(customer);
                payment.setStaff(staff);
                payment.setAmount(BigDecimal.valueOf(50.25));
                paymentDao.save(payment);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    private void customerReturnInventoryToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                Rental rental = rentalDao.getAnyUnreturnedRental();
                rental.setReturnDate(LocalDateTime.now());
                rentalDao.save(rental);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            Customer customer = new Customer();
            try {
                Store store = storeDao.getItems(0, 1).get(0);
                City city = cityDao.getByName("Batna");

                Address address = new Address();
                address.setAddress("Schimborner str. 15");
                address.setPhone("1684168468");
                address.setCity(city);
                address.setDistrict("Hosbach");
                address = addressDao.save(address);

                customer.setActive(true);
                customer.setEmail("test@test.com");
                customer.setAddress(address);
                customer.setStore(store);
                customer.setFirstName("TestName");
                customer.setLastName("TestLastname");

                customer = customerDao.save(customer);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
            return customer;
        }
    }

}
