package nl.rabobank.user.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.user.dataprovider.UserDao;
import nl.rabobank.user.usecase.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserDao {

  private final UserMongoRepository mongoRepository;

  @Override
  public Optional<User> findBy(final String username, final String password) {
    return mongoRepository.findByUsernameAndPassword(username, password);
  }

  @Override
  public Optional<User> findBy(final String username) {
    return mongoRepository.findByUsername(username);
  }

  @Override
  public Optional<User> findByDocument(final String document) {
    return mongoRepository.findByDocument(document);
  }

}
