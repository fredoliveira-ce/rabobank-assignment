package nl.rabobank.account.repository;

import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.entity.PaymentAccountEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentAccountMongoRepository extends MongoRepository<PaymentAccountEntity, ObjectId> {

    Optional<Account> findByNumber(String number);

}
