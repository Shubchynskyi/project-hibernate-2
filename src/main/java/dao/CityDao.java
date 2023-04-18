package dao;

import entity.address.City;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDao extends GenericDAO<City> {
    public CityDao(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String name) {
        String hql = """
                SELECT c FROM City c
                WHERE c.city = :NAME
                """;
        Query<City> query = getCurrentSession().createQuery(hql, City.class);
        query.setParameter("NAME", name);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
