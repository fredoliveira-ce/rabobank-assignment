package nl.rabobank.account.mapper;

import lombok.experimental.UtilityClass;
import nl.rabobank.account.entity.PaymentAccountEntity;
import nl.rabobank.account.entity.SavingsAccountEntity;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.account.usecase.PaymentAccount;
import nl.rabobank.account.usecase.SavingsAccount;

@UtilityClass
public class AccountMapper {

  public static SavingsAccountEntity toEntity(SavingsAccount account) {
    return SavingsAccountEntity.builder()
      .holderName(account.getHolderName())
      .holderDocument(account.getHolderDocument())
      .number(account.getNumber())
      .balance(account.getBalance())
      .build();
  }

  public static Account from(SavingsAccountEntity account) {
    return SavingsAccount.builder()
      .holderName(account.getHolderName())
      .holderDocument(account.getHolderDocument())
      .number(account.getNumber())
      .balance(account.getBalance())
      .build();
  }

  public static PaymentAccountEntity toEntity(PaymentAccount account) {
    return PaymentAccountEntity.builder()
      .holderName(account.getHolderName())
      .holderDocument(account.getHolderDocument())
      .number(account.getNumber())
      .balance(account.getBalance())
      .build();
  }

  public static Account from(PaymentAccountEntity account) {
    return PaymentAccount.builder()
      .holderName(account.getHolderName())
      .holderDocument(account.getHolderDocument())
      .number(account.getNumber())
      .balance(account.getBalance())
      .build();
  }
}
