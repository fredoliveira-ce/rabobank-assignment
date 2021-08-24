package nl.rabobank.account.repository;

import lombok.RequiredArgsConstructor;
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
public class PaymentAccountRepository implements AccountStrategyDao {

  private final PaymentAccountMongoRepository paymentAccountMongoRepository;

  @Override
  public Account save(final Account account) {
    return AccountMapper.from(
      paymentAccountMongoRepository.save(
        requireNonNull(toEntity((PaymentAccount) account)))
    );
  }

  @Override
  public Optional<Account> findBy(final String document, final AccountType type) {
    return paymentAccountMongoRepository.findByHolderDocument(document)
      .map(entity -> SavingsAccount.builder()
        .number(entity.getNumber())
        .holderDocument(entity.getHolderDocument())
        .holderName(entity.getHolderName())
        .build());
  }

  @Override
  public AccountType getType() {
    return AccountType.PAYMENT_ACCOUNT;
  }

}
