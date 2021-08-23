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

  public Optional<User> findUser(final String username, final String password) {
    return dao.findUser(username, password);
  }

  public User getUserDetails(final String username) {
    return dao.findByUsername(username)
      .orElseThrow(() -> new UserNotFoundException(username));
  }

  public Optional<User> findByDocument(final String document) {
    return dao.findByDocument(document);
  }
}
