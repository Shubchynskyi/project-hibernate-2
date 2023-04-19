package dao;

import entity.film.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDao extends GenericDAO<Film> {
    public FilmDao(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilmForRent() {
        String hql = """
                SELECT f FROM Film f
                WHERE f.id NOT IN (SELECT DISTINCT film.id FROM Inventory)
                """;
        Query<Film> query = getCurrentSession().createQuery(hql, Film.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
