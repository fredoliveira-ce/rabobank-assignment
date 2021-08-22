package nl.rabobank.account.repository;

import nl.rabobank.account.Account;
import nl.rabobank.account.entity.PaymentAccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentAccountMongoRepository extends MongoRepository<PaymentAccountEntity, String> {

    Optional<Account> findByNumber(String number);

}
