package nl.rabobank.authorization.mapper;

import lombok.experimental.UtilityClass;
import nl.rabobank.authorization.usecase.PowerOfAttorney;
import nl.rabobank.authorization.entity.PowerOfAttorneyEntity;

@UtilityClass
public class PowerOfAttorneyMapper {

  public static PowerOfAttorneyEntity toEntity(PowerOfAttorney powerOfAttorney) {
    return PowerOfAttorneyEntity.builder()
      .granteeDocument(powerOfAttorney.getGranteeDocument())
      .grantorDocument(powerOfAttorney.getGrantorDocument())
      .authorization(powerOfAttorney.getAuthorization())
      .account(powerOfAttorney.getAccount())
      .build();
  }

  public static PowerOfAttorney from(PowerOfAttorneyEntity entity) {
    return PowerOfAttorney.builder()
      .granteeDocument(entity.getGranteeDocument())
      .grantorDocument(entity.getGrantorDocument())
      .authorization(entity.getAuthorization())
      .account(entity.getAccount())
      .build();
  }
}
