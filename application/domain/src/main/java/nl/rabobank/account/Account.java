package nl.rabobank.account;

public interface Account {

  AccountType getType();
  String getNumber();
  String getHolderName();
  String getHolderDocument();
  Double getBalance();

}
