package nl.rabobank.authorization.dataprovider;

import nl.rabobank.authorization.usecase.PowerOfAttorney;

import java.util.List;

public interface PowerOfAttorneyDao {

  PowerOfAttorney save(PowerOfAttorney powerOfAttorney);

  List<PowerOfAttorney> findByGranteeDocument(String user);

}
