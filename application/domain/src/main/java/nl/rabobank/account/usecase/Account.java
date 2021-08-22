package nl.rabobank.account.usecase;

public interface Account {

  AccountType getType();
  String getNumber();
  String getHolderName();
  String getHolderDocument();
  Double getBalance();

}
