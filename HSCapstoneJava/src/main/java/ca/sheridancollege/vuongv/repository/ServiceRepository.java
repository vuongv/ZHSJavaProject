package ca.sheridancollege.vuongv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.Customer;
import ca.sheridancollege.vuongv.bean.WorkService;

public interface ServiceRepository extends JpaRepository<WorkService, Long> {
	public WorkService findByServiceName(String serviceName);
}
