package diploma.repos;

import diploma.domain.entities.Customer;
import diploma.domain.entities.Report;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepo extends CrudRepository<Report, Long> {
    List<Report> findByOwner(Customer owner);
}
