package nl.rabobank.authorization.mapper;

import lombok.experimental.UtilityClass;
import nl.rabobank.authorization.PowerOfAttorney;
import nl.rabobank.authorization.entity.PowerOfAttorneyEntity;

@UtilityClass
public class PowerOfAttorneyMapper {

  public static PowerOfAttorneyEntity toEntity(PowerOfAttorney powerOfAttorney) {
    return PowerOfAttorneyEntity.builder()
      .granteeName(powerOfAttorney.getGranteeName())
      .grantorName(powerOfAttorney.getGrantorName())
      .authorization(powerOfAttorney.getAuthorization())
      .account(powerOfAttorney.getAccount())
      .build();
  }

  public static PowerOfAttorney from(PowerOfAttorneyEntity entity) {
    return PowerOfAttorney.builder()
      .granteeName(entity.getGranteeName())
      .grantorName(entity.getGrantorName())
      .authorization(entity.getAuthorization())
      .account(entity.getAccount())
      .build();
  }
}
