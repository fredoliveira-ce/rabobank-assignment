package nl.rabobank.user.usecase;

import lombok.RequiredArgsConstructor;
import nl.rabobank.user.dataprovider.UserDao;
import nl.rabobank.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserDao dao;

  public Optional<User> find(final String username, final String password) {
    return dao.findBy(username, password);
  }

  public User find(final String username) {
    return dao.findBy(username)
      .orElseThrow(() -> new UserNotFoundException(username));
  }

  public Optional<User> findByDocument(final String document) {
    return dao.findByDocument(document);
  }
}
