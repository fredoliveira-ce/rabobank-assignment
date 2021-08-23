package nl.rabobank.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SelfPowerOfAttorneyNotAllowedException extends RuntimeException {

  public SelfPowerOfAttorneyNotAllowedException(String granteeDocument) {
    super(String.format("You (%s) are trying to create a power of attorney for yourself.", granteeDocument));
  }

}
