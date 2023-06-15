package sorivma.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sorivma.HibernateUtils;
import sorivma.entity.Manufactuer;

import java.util.List;

public class HibernateManufactuersRepo {
    private final SessionFactory sessionFactory;

    public HibernateManufactuersRepo() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    public List<Manufactuer> getManufactuers(){
        try(Session session = sessionFactory.openSession()){
            return session.
                    createQuery("SELECT m FROM Manufactuer m", Manufactuer.class)
                    .getResultList();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
