package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Takaseev", (byte) 62);
        userDao.saveUser("Petya", "Peterson", (byte) 32);
        userDao.saveUser("Fedorov", "Kalashnikov", (byte) 44);
        userDao.saveUser("Tanya", "Potterson", (byte) 20);
        userDao.removeUserById(2);
        System.out.println(userDao.getAllUsers().toString());

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
