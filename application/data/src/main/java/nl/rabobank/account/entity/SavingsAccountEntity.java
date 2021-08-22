package nl.rabobank.account.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "savings_account")
public class SavingsAccountEntity {

  @Id
  private ObjectId id;

  @Indexed(unique = true)
  private String number;
  private String holderName;
  private String holderDocument;
  private Double balance;
}
