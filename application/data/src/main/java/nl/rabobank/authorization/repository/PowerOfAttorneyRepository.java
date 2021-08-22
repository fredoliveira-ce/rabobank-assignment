package nl.rabobank.authorization.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.Account;
import nl.rabobank.authorization.PowerOfAttorney;
import nl.rabobank.authorization.dataprovider.PowerOfAttorneyDao;
import org.springframework.stereotype.Repository;

import java.util.List;

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
  public List<Account> find(String user) {
    return List.of();
  }
}
