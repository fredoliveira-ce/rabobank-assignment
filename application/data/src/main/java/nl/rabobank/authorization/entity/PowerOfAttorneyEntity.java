package nl.rabobank.authorization.entity;

import lombok.Builder;
import lombok.Data;
import nl.rabobank.account.usecase.Account;
import nl.rabobank.authorization.usecase.Authorization;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "power_of_attorney")
public class PowerOfAttorneyEntity {

  @Id
  private ObjectId id;
  @Indexed(unique = true)
  private String grantorDocument;
  @Indexed(unique = true)
  private String granteeDocument;
  private Account account;
  private Authorization authorization;

}
