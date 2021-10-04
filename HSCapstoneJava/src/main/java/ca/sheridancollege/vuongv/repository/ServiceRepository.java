package ca.sheridancollege.vuongv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.WorkService;

public interface ServiceRepository extends JpaRepository<WorkService, Long> {
	
	public WorkService findByServiceName(String serviceName);

	public Boolean existsByServiceName(String serviceName);
	
	public List<WorkService> findByServiceNameIgnoreCaseContaining  (String serviceName);
}
