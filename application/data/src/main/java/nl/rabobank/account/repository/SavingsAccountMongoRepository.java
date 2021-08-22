package nl.rabobank.account.repository;

import nl.rabobank.account.entity.SavingsAccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SavingsAccountMongoRepository extends MongoRepository<SavingsAccountEntity, String> {

  Optional<SavingsAccountEntity> findByNumber(String number);

}
