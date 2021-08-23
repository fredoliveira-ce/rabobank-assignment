package nl.rabobank.authorization.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import nl.rabobank.authorization.mapper.PowerOfAttorneyMapper;
import nl.rabobank.authorization.usecase.Authorization;
import nl.rabobank.authorization.usecase.PowerOfAttorney;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nl.rabobank.authorization.mapper.PowerOfAttorneyMapper.from;
import static nl.rabobank.authorization.mapper.PowerOfAttorneyMapper.toEntity;

@Repository
@RequiredArgsConstructor
public class PowerOfAttorneyRepository implements PowerOfAttorneyDao {

  private final PowerOfAttorneyMongoRepository mongoRepository;

  @Override
  public PowerOfAttorney save(final PowerOfAttorney powerOfAttorney) {
    return from(mongoRepository.save(toEntity(powerOfAttorney)));
  }

  @Override
  public List<PowerOfAttorney> findByGranteeDocument(final String document) {
    return mongoRepository.findByGranteeDocument(document).stream()
      .map(PowerOfAttorneyMapper::from)
      .collect(Collectors.toList());
  }

  @Override
  public Optional<PowerOfAttorney> findBy(final String granteeDocument, final String grantorDocument, final String authorization) {
    return mongoRepository
      .findByGranteeDocumentAndGrantorDocumentAndAuthorization(
        granteeDocument, grantorDocument, Authorization.valueOf(authorization))
      .map(PowerOfAttorneyMapper::from);
  }
}
