package nl.rabobank.account.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.mapper.AccountMapper;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.usecase.AccountType;
import nl.rabobank.account.usecase.PaymentAccount;
import nl.rabobank.account.usecase.SavingsAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static nl.rabobank.account.mapper.AccountMapper.toEntity;

@Repository
@RequiredArgsConstructor
public class AccountRepository implements AccountDao {

  private final SavingsAccountMongoRepository savingsAccountMongoRepository;
  private final PaymentAccountMongoRepository paymentAccountMongoRepository;

  @Override
  public Account save(final Account account) {
    return switch (account.getType()) {
      case PAYMENT_ACCOUNT -> AccountMapper.from(
        paymentAccountMongoRepository.save(
          requireNonNull(toEntity((PaymentAccount) account)))
      );
      case SAVINGS_ACCOUNT -> AccountMapper.from(
        savingsAccountMongoRepository.save(
          requireNonNull(toEntity((SavingsAccount) account)))
      );
    };
  }

  @Override
  public Optional<Account> findBy(final String document, final AccountType type) {
    return switch (type) {
      case PAYMENT_ACCOUNT ->
        paymentAccountMongoRepository.findByHolderDocument(document)
          .map(entity -> SavingsAccount.builder()
            .number(entity.getNumber())
            .holderDocument(entity.getHolderDocument())
            .holderName(entity.getHolderName())
            .build());
      case SAVINGS_ACCOUNT ->
        savingsAccountMongoRepository.findByHolderDocument(document)
          .map(entity -> PaymentAccount.builder()
            .number(entity.getNumber())
            .holderDocument(entity.getHolderDocument())
            .holderName(entity.getHolderName())
            .build());
    };
  }

}
