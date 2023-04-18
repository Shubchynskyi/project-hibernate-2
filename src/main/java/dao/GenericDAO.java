package dao;

//import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import jakarta.persistence.*;
import java.util.List;

public abstract class GenericDAO<T> {
    private final Class<T> clazz;

    private final SessionFactory sessionFactory;

    public GenericDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public T getById(int id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int offset, int count) {
        System.out.println("getItems");
        System.out.println(getCurrentSession().isOpen());
//        System.out.println(session);
        Query query = getCurrentSession().createQuery("FROM " + clazz.getName(), clazz);
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> findAll() {
        return getCurrentSession().createQuery("FROM " + clazz.getName(), clazz).list();
    }

    public T save(T entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public T update(T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().remove(entity);
    }

    public void deleteById(int entityId) {
        T entity = getById(entityId);
        delete((entity));
    }

    protected Session getCurrentSession() {

        return sessionFactory.getCurrentSession();
    }

}
