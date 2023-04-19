package dao;


import entity.store.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class RentalDao extends GenericDAO<Rental> {
    public RentalDao(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getAnyUnreturnedRental() {
        String hql = """
                SELECT r FROM Rental r 
                WHERE r.returnDate IS NULL
                """;
        Query<Rental> query = getCurrentSession().createQuery(hql, Rental.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
