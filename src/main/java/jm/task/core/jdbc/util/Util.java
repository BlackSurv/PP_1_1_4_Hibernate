package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
@EnableTransactionManagement
@org.springframework.context.annotation.Configuration
@ComponentScan(value = "jm.task.core.jdbc")
public class Util {

    public final Logger logger = Logger.getLogger(Util.class.getName());
    private final String URL = "jdbc:mysql://localhost:3306/dbtest?useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.username", USER)
                        .setProperty("hibernate.connection.password", PASSWORD)
                        .setProperty("hibernate.show_sql", "true")
                        .setProperty("hibernate.hbm2ddl", "update")
                        .addAnnotatedClass(jm.task.core.jdbc.model.User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return sessionFactory;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed()) {
                logger.log(Level.INFO, "Соединение с БД установлено");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Не удалось создать соединение с БД");
        }
        return connection;
    }
}
