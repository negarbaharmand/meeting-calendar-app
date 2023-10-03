package org.example.dao.impl;

import org.example.dao.UserDao;
import org.example.exception.AuthenticationFailedException;
import org.example.exception.MySQLException;
import org.example.exception.UserExpiredException;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User createUser(String username) {
        String query = "Insert into users(username, _password) values(?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            User user = new User(username);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new MySQLException("Creating user failed, no rows affected");
            }
            return user;
        } catch (SQLException e) {
            throw new MySQLException("Error occurred while creating user: " + username, e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE USERNAME = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String foundUsername = resultSet.getString("USERNAME");
                String foundPassword = resultSet.getString("_PASSWORD");
                boolean foundExpired = resultSet.getBoolean("EXPIRED");
                User user = new User(foundUsername, foundPassword, foundExpired);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new MySQLException("Error occurred while finding user by username: " + username, e);
        }
    }

    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException {
        String query = "select * from users where username = ? and _password = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String foundPassword = resultSet.getString("_PASSWORD");
                boolean foundExpired = resultSet.getBoolean("EXPIRED");

                if (foundExpired) {
                    throw new UserExpiredException("User account has expired");
                }

                if (user.getPassword().equals(foundPassword)) {
                    return true;
                } else {
                    throw new AuthenticationFailedException("Authentication failed");
                }
            } else {
                throw new AuthenticationFailedException("User not found");
            }

        } catch (SQLException e) {
            throw new MySQLException("Error occurred while authenticating user: " + user.getUsername(), e);
        }
    }


    /* Class solution:
    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException {
        String query = "SELECT * FROM USERS WHERE USERNAME = ? and _PASSWORD = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                boolean isExpired = resultSet.getBoolean("EXPIRED");
                if (isExpired) {
                    throw new UserExpiredException("User is Expired. username: " + user.getUsername());
                }

            } else {
                throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
            }

            return true;

        } catch (SQLException e) {
            throw new MySQLException("Error occurred while authenticating user by username: " + user.getUsername(), e);
        }

    }
     */
}

