package dao;

import entity.store.Inventory;
import org.hibernate.SessionFactory;

public class InventoryDao extends GenericDAO<Inventory> {
    public InventoryDao(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
