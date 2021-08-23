package nl.rabobank.authorization.repository;

import nl.rabobank.authorization.entity.PowerOfAttorneyEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PowerOfAttorneyMongoRepository extends MongoRepository<PowerOfAttorneyEntity, ObjectId> {

    List<PowerOfAttorneyEntity> findByGranteeDocument(String document);

}
