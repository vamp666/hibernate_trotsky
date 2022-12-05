package jm.task.core.jdbc.dao;

import com.mysql.cj.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transaction;
import java.sql.SQLException;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    Session session = Util.getSessionFactory().openSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        org.hibernate.Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE Users (\n" +
                    "    id INT NOT NULL AUTO_INCREMENT,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    lastName VARCHAR(255),\n" +
                    "    age INT NOT NULL,\n" +
                    "PRIMARY KEY (id));";
            org.hibernate.query.Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
                System.out.println(e);
            }
        }
    }

    @Override
    public void dropUsersTable() {
        org.hibernate.Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE Users";
            org.hibernate.query.Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            //session.close();
        } catch (Exception e) {
            if (transaction != null) {
                //transaction.rollback();
                System.out.println(e);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        org.hibernate.Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setLastName(lastName);

            session.save(user);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        org.hibernate.Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.load(User.class,id);
            session.delete(user);

            session.flush();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        org.hibernate.Transaction transaction = null;
        List<User> data = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            criteriaQuery.from(User.class);
            data = session.createQuery(criteriaQuery).getResultList();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return data;
    }

    @Override
    public void cleanUsersTable() {
        org.hibernate.Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DELETE FROM Users";

            org.hibernate.query.Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
