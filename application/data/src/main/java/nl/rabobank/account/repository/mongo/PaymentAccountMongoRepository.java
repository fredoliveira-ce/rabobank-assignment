package nl.rabobank.account.repository.mongo;

import nl.rabobank.account.entity.PaymentAccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentAccountMongoRepository extends MongoRepository<PaymentAccountEntity, String> {

  Optional<PaymentAccountEntity> findByHolderDocument(String document);

}
