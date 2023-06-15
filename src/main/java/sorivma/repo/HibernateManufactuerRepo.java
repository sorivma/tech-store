package sorivma.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sorivma.HibernateUtils;
import sorivma.entity.Manufactuer;

import java.util.List;

public class HibernateManufactuerRepo {
    private SessionFactory sessionFactory;

    public HibernateManufactuerRepo() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    public List<Manufactuer> getManufactuers(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT m FROM Manufactuer m", Manufactuer.class)
                    .getResultList();
        }
    }
}
