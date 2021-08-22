package nl.rabobank.authorization.repository;

import nl.rabobank.authorization.entity.PowerOfAttorneyEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PowerOfAttorneyMongoRepository extends MongoRepository<PowerOfAttorneyEntity, ObjectId> {

}
