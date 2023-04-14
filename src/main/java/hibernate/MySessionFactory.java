package hibernate;

import entity.film.Film;
import jakarta.persistence.Entity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import java.util.Set;

public class MySessionFactory {

    private static MySessionFactory instance;

    private final SessionFactory sessionFactory;

    public MySessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Film.class);

        String packageName = "entity";
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        for (Class<?> entityClass : entityClasses) {
            System.out.println(entityClass.getName());
        }

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new MySessionFactory();
        }
        return instance.sessionFactory;
    }



}
