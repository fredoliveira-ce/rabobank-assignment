package nl.rabobank.authorization.dataprovider;

import nl.rabobank.authorization.usecase.PowerOfAttorney;

import java.util.List;
import java.util.Optional;

public interface PowerOfAttorneyDao {

  PowerOfAttorney save(PowerOfAttorney powerOfAttorney);

  List<PowerOfAttorney> findByGranteeDocument(String document);

  Optional<PowerOfAttorney> findBy(String granteeDocument, String grantorDocument, String authorization);
}
