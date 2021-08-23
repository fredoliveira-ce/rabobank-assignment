package nl.rabobank.user.dataprovider;

import nl.rabobank.user.usecase.User;

import java.util.Optional;

public interface UserDao {

  Optional<User> findBy(String username, String password);

  Optional<User> findBy(String username);

  Optional<User> findByDocument(String document);
}
