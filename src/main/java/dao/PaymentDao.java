package dao;

import entity.store.Payment;
import org.hibernate.SessionFactory;

public class PaymentDao extends GenericDAO<Payment> {
    public PaymentDao(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
