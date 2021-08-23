package nl.rabobank.authorization.exception;

import nl.rabobank.authorization.usecase.PowerOfAttorney;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PowerOfAttorneyAlreadyExistsException extends RuntimeException {

  public PowerOfAttorneyAlreadyExistsException(PowerOfAttorney powerOfAttorney) {
    super(String.format("The grantor %s already has a power of attorney registered for %s with %s authorization.",
      powerOfAttorney.getGrantorDocument(), powerOfAttorney.getGranteeDocument(), powerOfAttorney.getAuthorization()));
  }

}
