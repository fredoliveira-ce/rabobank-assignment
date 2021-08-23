package nl.rabobank.authorization.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import nl.rabobank.authorization.mapper.PowerOfAttorneyMapper;
import nl.rabobank.authorization.usecase.PowerOfAttorney;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static nl.rabobank.authorization.mapper.PowerOfAttorneyMapper.from;
import static nl.rabobank.authorization.mapper.PowerOfAttorneyMapper.toEntity;

@Repository
@RequiredArgsConstructor
public class PowerOfAttorneyRepository implements PowerOfAttorneyDao {

  private final PowerOfAttorneyMongoRepository mongoRepository;

  @Override
  public PowerOfAttorney save(PowerOfAttorney powerOfAttorney) {
    return from(mongoRepository.save(toEntity(powerOfAttorney)));
  }

  @Override
  public List<PowerOfAttorney> findByGranteeDocument(String document) {
    return mongoRepository.findByGranteeDocument(document).stream()
      .map(PowerOfAttorneyMapper::from)
      .collect(Collectors.toList());
  }
}
