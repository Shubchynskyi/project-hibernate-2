package hibernate;

import entity.film.Film;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        try (Session session = MySessionFactory.getSessionFactory().openSession()) {
            System.out.println("test");
            String hql = """
                    SELECT f FROM Film f
                    """;
            List<Film> list = session.createQuery(hql, Film.class).list();
            list.forEach(System.out::println);
        }

    }

}
