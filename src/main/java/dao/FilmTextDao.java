package dao;


import entity.film.FilmText;
import org.hibernate.SessionFactory;

public class FilmTextDao extends GenericDAO<FilmText> {
    public FilmTextDao(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
