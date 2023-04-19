package dao;

import entity.film.Feature;
import org.hibernate.SessionFactory;

public class FeatureDao extends GenericDAO<Feature> {
    public FeatureDao(SessionFactory sessionFactory) {
        super(Feature.class, sessionFactory);
    }
}
