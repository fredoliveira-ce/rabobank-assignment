package nl.rabobank.user.entity;

import lombok.Builder;
import lombok.Data;
import nl.rabobank.user.usecase.RoleType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "user")
public class UserEntity {

  @Id
  private ObjectId id;

  @Indexed(unique = true)
  private String username;
  private String password;
  private RoleType role;

}
