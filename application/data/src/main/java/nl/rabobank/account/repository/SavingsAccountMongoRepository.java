package nl.rabobank.account.repository;

import nl.rabobank.account.entity.SavingsAccountEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SavingsAccountMongoRepository extends MongoRepository<SavingsAccountEntity, ObjectId> {

  Optional<SavingsAccountEntity> findByNumber(String number);

}
