package dao;

import entity.store.Staff;
import org.hibernate.SessionFactory;

public class StaffDao extends GenericDAO<Staff> {
    public StaffDao(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
