package dao;

import entity.address.Country;
import org.hibernate.SessionFactory;

public class CountryDao extends GenericDAO<Country> {
    public CountryDao(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
