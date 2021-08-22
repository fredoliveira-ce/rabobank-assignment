package nl.rabobank.user.repository;

import nl.rabobank.user.entity.UserEntity;
import nl.rabobank.user.usecase.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserEntity, ObjectId> {

  Optional<User> findByUsernameAndPassword(String username, String password);

  Optional<User> findByUsername(String username);
}
