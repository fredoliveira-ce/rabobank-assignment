package nl.rabobank.authorization.usecase;

import mock.PowerOfAttorneyMock;
import mock.UserMock;
import nl.rabobank.account.exception.AccountWithoutRegisterException;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import nl.rabobank.authorization.exception.PowerOfAttorneyAlreadyExistsException;
import nl.rabobank.authorization.exception.SelfPowerOfAttorneyNotAllowedException;
import nl.rabobank.user.usecase.User;
import nl.rabobank.user.usecase.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Runs all tests for service layer of power of attorney validation")
class PowerOfAttorneyValidatorTest {

  private PowerOfAttorneyValidator service;
  private PowerOfAttorneyDao dao;
  private UserService userService;

  @BeforeEach
  void beforeEach() {
    dao = Mockito.mock(PowerOfAttorneyDao.class);
    userService = Mockito.mock(UserService.class);
    service = new PowerOfAttorneyValidator(dao, userService);
  }

  @Test
  @DisplayName("should validate a power of attorney without throw exceptions.")
  void validatePowerOfAttorneySuccessfully() {
    PowerOfAttorney powerOfAttorney = PowerOfAttorneyMock.getOne();
    Mockito.when(userService.find(any())).thenReturn(UserMock.getOne());
    Mockito.when(userService.findByDocument(any())).thenReturn(Optional.of(UserMock.getOne()));

    assertDoesNotThrow(() -> service.validate(
      powerOfAttorney.getGranteeDocument(),
      powerOfAttorney.getGrantorDocument(),
      powerOfAttorney.getAuthorization()));
  }

  @Test
  @DisplayName("should validate and throw PowerOfAttorneyAlreadyExistsException.")
  void validateDuplicity() {
    PowerOfAttorney powerOfAttorney = PowerOfAttorneyMock.getOne();
    Mockito.when(userService.find(any())).thenReturn(UserMock.getOne());
    Mockito.when(userService.findByDocument(any())).thenReturn(Optional.of(UserMock.getOne()));
    Mockito.when(dao.findBy(any(), any(), any())).thenReturn(Optional.of(PowerOfAttorneyMock.getOne()));

    final Exception exception = assertThrows(PowerOfAttorneyAlreadyExistsException.class,
      () -> service.validate(
        powerOfAttorney.getGranteeDocument(),
        powerOfAttorney.getGrantorDocument(),
        powerOfAttorney.getAuthorization())
    );

    assertTrue(exception.getMessage()
      .contains("The grantor 432141 already has a power of attorney registered for 123412 with READ authorization."));
  }

  @Test
  @DisplayName("should validate and throw AccountWithoutRegisterException.")
  void validateAccountWithoutRegisterException() {
    PowerOfAttorney powerOfAttorney = PowerOfAttorneyMock.getOne();
    Mockito.when(userService.find(any())).thenReturn(UserMock.getOne());
    Mockito.when(userService.findByDocument(any())).thenReturn(Optional.empty());

    final Exception exception = assertThrows(AccountWithoutRegisterException.class,
      () -> service.validate(
        powerOfAttorney.getGranteeDocument(),
        powerOfAttorney.getGrantorDocument(),
        powerOfAttorney.getAuthorization())
    );

    assertTrue(exception.getMessage()
      .contains("Registration for account with document 123412 not found."));
  }

  @Test
  @DisplayName("should validate and throw SelfPowerOfAttorneyNotAllowedException.")
  void validateSelfPowerOfAttorneyNotAllowedException() {
    PowerOfAttorney powerOfAttorney = PowerOfAttorneyMock.getOne();
    User user = UserMock.getOne();
    Mockito.when(userService.find(any())).thenReturn(user);
    Mockito.when(userService.findByDocument(any())).thenReturn(Optional.of(user));

    final Exception exception = assertThrows(SelfPowerOfAttorneyNotAllowedException.class,
      () -> service.validate(
        user.getDocument(),
        powerOfAttorney.getGrantorDocument(),
        powerOfAttorney.getAuthorization())
    );

    assertTrue(exception.getMessage()
      .contains("You (523490) are trying to create a power of attorney for yourself."));
  }

}
