package nl.rabobank.authorization.usecase;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.exception.AccountWithoutRegisterException;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import nl.rabobank.authorization.exception.PowerOfAttorneyAlreadyExistsException;
import nl.rabobank.authorization.exception.SelfPowerOfAttorneyNotAllowedException;
import nl.rabobank.user.usecase.User;
import nl.rabobank.user.usecase.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PowerOfAttorneyValidator {

  private final PowerOfAttorneyDao dao;
  private final UserService userService;

  public User validate(
    final String granteeDocument, final String grantor, final String authorization
  ) {
    var user = validateSelfPowerOfAttorney(granteeDocument, grantor);
    validateDuplicatedPowerOfAttorney(granteeDocument, authorization, user);
    validateGranteeRegistration(granteeDocument);

    return user;
  }

  private void validateDuplicatedPowerOfAttorney(
    final String granteeDocument, final String authorization, final User user
  ) {
    dao.findBy(granteeDocument, user.getDocument(), authorization)
      .ifPresent(powerOfAttorney -> {
        throw new PowerOfAttorneyAlreadyExistsException(powerOfAttorney);
      });
  }

  private User validateSelfPowerOfAttorney(final String granteeDocument, final String grantor) {
    var user = userService.getUserDetails(grantor);

    if (granteeDocument.equals(user.getDocument())) {
      throw new SelfPowerOfAttorneyNotAllowedException(granteeDocument);
    }
    return user;
  }

  private void validateGranteeRegistration(String granteeDocument) {
    if (userService.findByDocument(granteeDocument).isEmpty()) {
      throw new AccountWithoutRegisterException(granteeDocument);
    }
  }

}
