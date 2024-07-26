package defix.coffeedelivery.db;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(new File("src/main/resources/hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("Failed.");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
