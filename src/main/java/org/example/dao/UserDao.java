package org.example.dao;

import org.example.exception.AuthenticationFailedException;
import org.example.exception.UserExpiredException;
import org.example.model.User;

import java.util.Optional;

public interface UserDao {
    User createUser(String username);

    // if it may be null we use Optional container to check
    Optional<User> findByUsername(String username);

    boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException;
    // todo: Add method for updating users as needed

}
