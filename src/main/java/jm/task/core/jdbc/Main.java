package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Олег", "Павлов", (byte) 70);
        userService.saveUser("Дмитрий", "Иванов", (byte) 14);
        userService.saveUser("Ольга", "Бунина", (byte) 17);
        userService.saveUser("Наталья", "Павленко", (byte) 35);

        userService.getAllUsers().forEach(System.out::println);

        //userService.cleanUsersTable();
        //userService.dropUsersTable();
    }
}