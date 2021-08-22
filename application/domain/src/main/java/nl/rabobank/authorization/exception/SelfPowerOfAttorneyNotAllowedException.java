package nl.rabobank.authorization.exception;

public class SelfPowerOfAttorneyNotAllowedException extends RuntimeException {
  public SelfPowerOfAttorneyNotAllowedException(String granteeDocument) {
    super(String.format("You (%s) are trying to create a power of attorney for yourself.", granteeDocument));
  }
}
