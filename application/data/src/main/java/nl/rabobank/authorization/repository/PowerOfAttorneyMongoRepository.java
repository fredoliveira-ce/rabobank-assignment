package nl.rabobank.authorization.repository;

import nl.rabobank.authorization.entity.PowerOfAttorneyEntity;
import nl.rabobank.authorization.usecase.Authorization;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PowerOfAttorneyMongoRepository extends MongoRepository<PowerOfAttorneyEntity, ObjectId> {

  List<PowerOfAttorneyEntity> findByGranteeDocument(String document);

  Optional<PowerOfAttorneyEntity> findByGranteeDocumentAndGrantorDocumentAndAuthorization(
    String granteeDocument, String grantorDocument, Authorization authorization);
}
