package nl.rabobank.user.usecase;

import lombok.RequiredArgsConstructor;
import nl.rabobank.user.dataprovider.UserDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserDao dao;

  public Optional<User> findUser(final String username, final String password) {
    return dao.findUser(username, password);
  }

}
