package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {

        final UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        byte age = 18;
        byte age2 = 20;
        byte age3 = 21;
        byte age4 = 22;

        userService.saveUser("John", "Galt", age);
        userService.saveUser("Hank", "Rearden", age2);
        userService.saveUser("Dagny", "Taggart", age3);
        userService.saveUser("Ragnar", "Danneskjöld", age4 );

        List<User> userList = userService.getAllUsers();
        for (User u : userList) {
            System.out.println(u);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        }
}
