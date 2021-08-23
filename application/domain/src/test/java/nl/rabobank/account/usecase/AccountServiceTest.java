package nl.rabobank.account.usecase;

import mock.AccountMock;
import mock.UserMock;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.exception.AccountWithoutRegisterException;
import nl.rabobank.user.usecase.User;
import nl.rabobank.user.usecase.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Runs all tests for service layer of account entity")
class AccountServiceTest {

  private AccountDao dao;
  private AccountService service;
  private UserService userService;

  @BeforeEach void beforeEach() {
    userService = Mockito.mock(UserService.class);
    dao = Mockito.mock(AccountDao.class);
    service = new AccountService(dao, userService);
  }

  @Test
  @DisplayName("should save a new payment account")
  void saveNewPaymentAccountSuccessfully() {
    Account account = AccountMock.getOnePaymentAccount();
    Mockito.when(dao.save(account)).thenReturn(account);

    Account result = service.save(account);

    assertEquals(account, result);
    Mockito.verify(dao).save(account);
  }

  @Test
  @DisplayName("should save a new savings account")
  void saveNewSavingsAccountSuccessfully() {
    Account account = AccountMock.getOneSavingsAccount();
    Mockito.when(dao.save(account)).thenReturn(account);

    Account result = service.save(account);

    assertEquals(account, result);
    Mockito.verify(dao).save(account);
  }

  @Test
  @DisplayName("should validate a account")
  void validateNewAccountSuccessfully() {
    User user = UserMock.getOne();
    Mockito.when(userService.findByDocument(any())).thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> service.validate("11111111111"));
  }

  @Test
  @DisplayName("should validate an account and throw an AccountWithoutRegisterException")
  void validateAndThrowException() {
    Mockito.when(userService.findByDocument(any())).thenReturn(Optional.empty());

    final Exception exception = assertThrows(AccountWithoutRegisterException.class,
      () -> service.validate("11111111111")
    );

    assertTrue(exception.getMessage()
      .contains("Registration for account with document 11111111111 not found."));
  }
}
