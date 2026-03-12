package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection conn = Util.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGSERIAL PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age SMALLINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getConnection(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("lastName"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
