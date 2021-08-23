package nl.rabobank.authorization.usecase;

import mock.AccountMock;
import mock.PowerOfAttorneyMock;
import mock.UserMock;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.exception.AccountNotFoundException;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import nl.rabobank.user.usecase.User;
import nl.rabobank.user.usecase.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Runs all tests for service layer of power of attorney entity")
class PowerOfAttorneyServiceTest {

  private PowerOfAttorneyService service;
  private PowerOfAttorneyDao dao;
  private AccountDao accountDao;
  private UserService userService;

  @BeforeEach
  void beforeEach() {
    dao = Mockito.mock(PowerOfAttorneyDao.class);
    accountDao = Mockito.mock(AccountDao.class);
    userService = Mockito.mock(UserService.class);
    service = new PowerOfAttorneyService(dao, accountDao, userService);
  }

  @Test
  @DisplayName("should save a new power of attorney")
  void saveNewSuccessfully() {
    PowerOfAttorney powerOfAttorney = PowerOfAttorneyMock.getOne();
    Mockito.when(dao.save(powerOfAttorney)).thenReturn(powerOfAttorney);
    Mockito.when(accountDao.findBy(any(), any())).thenReturn(Optional.of(AccountMock.getOneSavingsAccount()));

    PowerOfAttorney result = service.save(powerOfAttorney);

    assertEquals(powerOfAttorney, result);
    Mockito.verify(dao).save(powerOfAttorney);
  }

  @Test
  @DisplayName("should throw an AccountNotFoundException when try to save a new power of attorney with invalid account")
  void saveAndThrowException() {
    Mockito.when(accountDao.findBy(any(), any())).thenReturn(Optional.empty());

    final Exception exception = assertThrows(AccountNotFoundException.class,
      () -> service.save(PowerOfAttorneyMock.getOne())
    );

    assertTrue(exception.getMessage()
      .contains("Account with type SAVINGS_ACCOUNT and document '432141' not found."));
  }

  @Test
  @DisplayName("should find power of attorneys and return with an empty result")
  void findAllWithEmptyResult() {
    Mockito.when(dao.findByGranteeDocument("11111111111")).thenReturn(List.of());
    Mockito.when(userService.find("user.test")).thenReturn(UserMock.getOne());

    List<PowerOfAttorney> result = service.find("user.test");

    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("should find power of attorneys and return with one result")
  void findByUsernameWithOneResult() {
    User user = UserMock.getOne();
    var loggedUser = "logged.user.test";
    PowerOfAttorney powerOfAttorney = PowerOfAttorneyMock.getOne();
    Mockito.when(dao.findByGranteeDocument(user.getDocument())).thenReturn(List.of(powerOfAttorney));
    Mockito.when(userService.find(loggedUser)).thenReturn(user);

    List<PowerOfAttorney> result = service.find(loggedUser);

    assertFalse(result.isEmpty());
    result.forEach(it -> {
      assertEquals(powerOfAttorney.getGrantorDocument(), it.getGrantorDocument());
      assertEquals(powerOfAttorney.getGranteeDocument(), it.getGranteeDocument());
      assertEquals(powerOfAttorney.getAuthorization(), it.getAuthorization());
      assertEquals(powerOfAttorney.getAccount(), it.getAccount());
    });
    Mockito.verify(dao).findByGranteeDocument(user.getDocument());
  }

}
