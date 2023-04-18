package dao;

import entity.address.Address;
import org.hibernate.SessionFactory;

public class AddressDao extends GenericDAO<Address> {
    public AddressDao(SessionFactory sessionFactory) {
        super(Address.class, sessionFactory);
    }
}
