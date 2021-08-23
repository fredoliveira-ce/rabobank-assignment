package nl.rabobank.user.dataprovider;

import nl.rabobank.user.usecase.User;

import java.util.Optional;

public interface UserDao {

  Optional<User> findUser(String username, String password);

  Optional<User> findByUsername(String username);

  Optional<User> findByDocument(String document);
}
