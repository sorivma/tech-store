package sorivma.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sorivma.HibernateUtils;
import sorivma.entity.Category;

import java.util.List;

public class HibernateCategoryRepo {
    private SessionFactory sessionFactory;

    public HibernateCategoryRepo() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    public List<Category> getCategories(){
        try(Session session = sessionFactory.openSession()){
            return session
                    .createQuery("SELECT c FROM Category c", Category.class)
                    .getResultList();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
