package customerservice.repository.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import customerservice.domain.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, ObjectId> {

}
