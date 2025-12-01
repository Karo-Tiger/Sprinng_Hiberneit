package hiber.service;

import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService  {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @PersistenceContext
   private EntityManager entityManager;

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional(readOnly = true)
   @Override
   public List<Car> listcar() {
      return userDao.listcar();
   }

   @Transactional(readOnly = true)
   @Override
   public Optional<User> findUserByCarModelAndSeries(String model, int series) {
      TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series", User.class);
      query.setParameter("model",model);
      query.setParameter("series",series);

      return query.getResultStream().findAny();
   }

}
