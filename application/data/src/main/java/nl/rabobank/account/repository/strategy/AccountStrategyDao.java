package nl.rabobank.account.repository.strategy;

import nl.rabobank.account.dataprovider.AccountDao;
import nl.rabobank.account.usecase.AccountType;

public interface AccountStrategyDao extends AccountDao {

  AccountType getType();

}
