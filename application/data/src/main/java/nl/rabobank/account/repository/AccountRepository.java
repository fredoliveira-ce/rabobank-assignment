package nl.rabobank.account.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.usecase.PaymentAccount;
import nl.rabobank.account.usecase.SavingsAccount;
import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.mapper.AccountMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository implements AccountDao {

  private final SavingsAccountMongoRepository savingsAccountMongoRepository;
  private final PaymentAccountMongoRepository paymentAccountMongoRepository;

  @Override
  public Account save(Account account) {
    if (account instanceof SavingsAccount) {
      return AccountMapper.from(
        savingsAccountMongoRepository.save(Objects.requireNonNull(AccountMapper.toEntity((SavingsAccount) account)))
      );
    }

    if (account instanceof PaymentAccount) {
      return AccountMapper.from(
        paymentAccountMongoRepository.save(Objects.requireNonNull(AccountMapper.toEntity((PaymentAccount) account)))
      );
    }
    return null;
  }

  @Override
  public Optional<Account> findAccount(Account account) {
    if (account instanceof SavingsAccount) {
      return savingsAccountMongoRepository.findByNumber(account.getNumber())
        .map(entity -> SavingsAccount.builder()
          .number(entity.getNumber())
          .holderDocument(entity.getHolderDocument())
          .holderName(entity.getHolderName())
          .build());
    }

    if (account instanceof PaymentAccount) {
      return paymentAccountMongoRepository.findByNumber(account.getNumber())
        .map(entity -> PaymentAccount.builder()
          .number(entity.getNumber())
          .holderDocument(entity.getHolderDocument())
          .holderName(entity.getHolderName())
          .build());
    }
    return Optional.empty();
  }

}
