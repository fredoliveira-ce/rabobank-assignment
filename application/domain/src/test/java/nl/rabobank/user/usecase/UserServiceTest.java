package nl.rabobank.user.usecase;

import mock.UserMock;
import nl.rabobank.user.dataprovider.UserDao;
import nl.rabobank.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Runs all tests for service layer of user entity")
class UserServiceTest {

  private UserService service;
  private UserDao dao;

  @BeforeEach void beforeEach() {
    dao = Mockito.mock(UserDao.class);
    service = new UserService(dao);
  }

  @Test
  @DisplayName("should find a user with username and password and return an empty result")
  void findByUsernameAndPasswordWithEmptyResult() {
    Mockito.when(dao.findBy(any(), any())).thenReturn(Optional.empty());

    Optional<User> user = service.find("user", "pwd");

    assertTrue(user.isEmpty());
    Mockito.verify(dao).findBy("user", "pwd");
  }

  @Test
  @DisplayName("should find a user with username and password and return one result")
  void findByUsernameAndPasswordWithOneResult() {
    User user = UserMock.getOne();
    Mockito.when(dao.findBy(any(), any())).thenReturn(Optional.of(user));

    Optional<User> result = service.find(user.getUsername(), user.getPassword());

    assertFalse(result.isEmpty());
    Mockito.verify(dao).findBy(result.get().getUsername(), result.get().getPassword());
  }

  @Test
  @DisplayName("should find a user with username and throw an UserNotFoundException")
  void findByUsernameAndThrowException() {
    Mockito.when(dao.findBy(any(), any())).thenReturn(Optional.empty());

    final Exception exception = assertThrows(UserNotFoundException.class,
      () -> service.find("user")
    );

    assertTrue(exception.getMessage()
      .contains("User 'user' not found."));
  }

  @Test
  @DisplayName("should find a user with username and return one result")
  void findByUsernameWithOneResult() {
    User user = UserMock.getOne();
    Mockito.when(dao.findBy(any())).thenReturn(Optional.of(user));

    User result = service.find(user.getUsername());

    assertEquals(user.getDocument(), result.getDocument());
    assertEquals(user.getPassword(), result.getPassword());
    assertEquals(user.getRole(), result.getRole());
    Mockito.verify(dao).findBy(result.getUsername());
  }

  @Test
  @DisplayName("should find a user with document and return an empty result")
  void findByDocumentWithEmptyResult() {
    Mockito.when(dao.findByDocument(any())).thenReturn(Optional.empty());

    Optional<User> user = service.findByDocument("000000000");

    assertTrue(user.isEmpty());
    Mockito.verify(dao).findByDocument("000000000");
  }

  @Test
  @DisplayName("should find a user with document and return one result")
  void findByDocumentWithOneResult() {
    User user = UserMock.getOne();
    Mockito.when(dao.findByDocument(any())).thenReturn(Optional.of(user));

    Optional<User> result = service.findByDocument(user.getDocument());

    assertFalse(result.isEmpty());
    Mockito.verify(dao).findByDocument(result.get().getDocument());
  }
}
