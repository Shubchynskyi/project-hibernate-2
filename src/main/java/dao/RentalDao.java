package dao;


import entity.store.Rental;
import org.hibernate.SessionFactory;

public class RentalDao extends GenericDAO<Rental> {
    public RentalDao(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }
}
