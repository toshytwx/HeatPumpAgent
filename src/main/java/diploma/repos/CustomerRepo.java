package diploma.repos;

import diploma.domain.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
}
