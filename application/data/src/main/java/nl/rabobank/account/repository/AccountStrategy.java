package nl.rabobank.account.repository;

import lombok.RequiredArgsConstructor;
import nl.rabobank.account.usecase.AccountType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.EnumMap;

@Component
@RequiredArgsConstructor
public class AccountStrategy {

  private final Collection<AccountStrategyDao> accountTypes;

  private final EnumMap<AccountType, AccountStrategyDao> factoryType =
    new EnumMap<>(AccountType.class);

  @PostConstruct
  public void init() {
    accountTypes.forEach(strategy -> factoryType.put(strategy.getType(), strategy));
  }

  public AccountStrategyDao getInstance(AccountType accountType) {
    return factoryType.get(accountType);
  }

}
