package sorivma.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sorivma.HibernateUtils;
import sorivma.entity.Product;

import java.util.List;

public class HibernateCatalogRepo {
    private final SessionFactory sessionFactory;

    public HibernateCatalogRepo() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    public List<Product> getProducts(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Product product){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.merge(product);
            tx.commit();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void addProduct(Product product) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(product);
            tx.commit();
        }
    }

    public void deleteProduct(Product product) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(product);
            transaction.commit();
        }
    }
}
