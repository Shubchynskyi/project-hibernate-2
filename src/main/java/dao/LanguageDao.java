package dao;

import entity.film.Language;
import org.hibernate.SessionFactory;

public class LanguageDao extends GenericDAO<Language> {
    public LanguageDao(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}