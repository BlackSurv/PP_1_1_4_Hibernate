package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    private final Logger logger;

    private static final String CREATE_USERS_QUERY = "CREATE TABLE IF NOT EXISTS USERS " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(64), " +
            " lastName VARCHAR(64), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";

    private static final String DROP_USERS_QUERY = "DROP TABLE IF EXISTS USERS";

    private static final String CLEAN_USERS_QUERY = "TRUNCATE TABLE users";

    public UserDaoHibernateImpl() {
        sessionFactory = new Util().getSessionFactory();
        logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createSQLQuery(CREATE_USERS_QUERY);
            query.executeUpdate();
        }
        logger.log(Level.INFO, "Таблица Users успешно создана");
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createSQLQuery(DROP_USERS_QUERY);
            query.executeUpdate();
        }
        logger.log(Level.INFO, "Таблица Users успешно удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
        logger.log(Level.INFO, "User с именем – {0} добавлен в базу данных", name);
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            session.remove(user);
        }
        logger.log(Level.INFO, "User с id – {0} удален из базы данных", id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            userList = session.createQuery(criteria).getResultList();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createSQLQuery(CLEAN_USERS_QUERY);
            query.executeUpdate();
        }
        logger.log(Level.INFO, "Таблица Users успешно очищена");
    }
}
