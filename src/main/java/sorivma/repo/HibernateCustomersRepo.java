package sorivma.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sorivma.HibernateUtils;
import sorivma.entity.HibernateCustomer;

import java.util.List;

public class HibernateCustomersRepo {
    private SessionFactory sessionFactory;

    public HibernateCustomersRepo() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    public List<HibernateCustomer> getCustomers(){
        try(Session session = sessionFactory.openSession()){
            return session
                    .createQuery("SELECT c FROM HibernateCustomer c", HibernateCustomer.class)
                    .getResultList();
        }
    }
}
