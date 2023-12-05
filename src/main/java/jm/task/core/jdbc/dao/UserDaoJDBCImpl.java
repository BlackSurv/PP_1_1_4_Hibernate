package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_USERS_QUERY = "CREATE TABLE IF NOT EXISTS USERS " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(64), " +
            " lastName VARCHAR(64), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";

    private static final String DROP_USERS_QUERY = "DROP TABLE IF EXISTS USERS";

    private static final String SAVE_USER_STATEMENT = "INSERT INTO USERS (name,lastName,age) VALUES (?,?,?)";

    private static final String REMOVE_USER_BY_ID_STATEMENT = "DELETE FROM USERS WHERE id=?";

    private static final String GET_ALL_USERS_QUERY = "SELECT id,name,lastName,age FROM users";

    private static final String CLEAN_USERS_UPDATE = "TRUNCATE TABLE users";

    private final Connection connection;

    private final Logger logger;

    public UserDaoJDBCImpl() {
        connection = new Util().getConnection();
        logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException("Could not create users: " + e.getMessage());
        }
        logger.log(Level.INFO, "Таблица Users успешно создана");
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_USERS_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException("Could not drop users table: " + e.getMessage());
        }
        logger.log(Level.INFO, "Таблица Users успешно удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_STATEMENT)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not save user: " + e.getMessage());

        }
        logger.log(Level.INFO, "User с именем – {0} добавлен в базу данных", name);
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_STATEMENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not remove user: " + e.getMessage());

        }
        logger.log(Level.INFO, "User с id – {0} удален из базы данных", id);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_QUERY);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not get all users: " + e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_UPDATE);
        } catch (SQLException e) {
            throw new RuntimeException("Could not clean user table: " + e.getMessage());
        }
        logger.log(Level.INFO, "Таблица Users успешно очищена");
    }
}
