package sorivma;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtils {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try {
            return new Configuration().configure(
                    new File("src\\main\\resources\\hibernate.cfg.xml")
            ).buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("Initialization failed" + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {return  sessionFactory;}

    public static void shutdown() {
        getSessionFactory().close();
    }
}

