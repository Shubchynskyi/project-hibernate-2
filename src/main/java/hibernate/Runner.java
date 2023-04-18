package hibernate;

import dao.*;
import entity.address.Address;
import entity.address.City;
import entity.store.Customer;
import entity.store.Store;

import jakarta.persistence.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

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
//        configuration.addAnnotatedClass(Film.class);

        String packageName = "entity";
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        for (Class<?> entityClass : entityClasses) {
            configuration.addAnnotatedClass(entityClass);
        }

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
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
//        System.out.println(customer);

//        try (Session session = runner.sessionFactory.openSession()) {
//            System.out.println("test");
//            String hql = """
//                    SELECT f FROM Country f
//                    """;
//            List<Country> list = session.createQuery(hql, Country.class).list();
//            list.forEach(System.out::println);
//        }

    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            System.err.println(session);
            Store store = storeDao.getItems(0, 1).get(0);

            System.err.println("store " + store.toString());

            City city = cityDao.getByName("Batna");

            Address address = new Address();
            address.setAddress("Schimborner str. 15");
            address.setPhone("1684168468");
            address.setCity(city);
            address.setDistrict("Hosbach");
            System.err.println("address" + address);
            address = addressDao.save(address);
            System.err.println("address saved: " + address);

            Customer customer = new Customer();
            customer.setActive((byte) 1);
            customer.setEmail("test@test.com");
            customer.setAddress(address);
            customer.setStore(store);
            customer.setFirstName("TestName");
            customer.setLastName("TestLastname");
            System.err.println("customer" + customer);
            customer = customerDao.save(customer);
            System.err.println("customer saved: " + customer.toString());

            transaction.commit();
            return customer;
        }
    }

}
