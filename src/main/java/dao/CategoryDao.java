package dao;


import entity.film.Category;
import org.hibernate.SessionFactory;

public class CategoryDao extends GenericDAO<Category> {
    public CategoryDao(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
