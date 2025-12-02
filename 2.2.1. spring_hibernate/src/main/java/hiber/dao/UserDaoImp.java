package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;
   @PersistenceContext
   private EntityManager entityManager;


   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<Car> listcar() {
      TypedQuery<Car> query= sessionFactory.getCurrentSession().createQuery("from Car");
      return query.getResultList();
   }

   @Transactional(readOnly = true)
   @Override
   public Optional<User> findUserByCarModelAndSeries(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series", User.class);
      query.setParameter("model",model);
      query.setParameter("series",series);
      return query.getResultStream().findAny();
   }
}
