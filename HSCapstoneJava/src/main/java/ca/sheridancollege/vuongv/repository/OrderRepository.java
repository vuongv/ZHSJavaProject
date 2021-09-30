package ca.sheridancollege.vuongv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.WorkOrder;

public interface OrderRepository extends JpaRepository<WorkOrder, Long> {
	
	public List<WorkOrder> findByWorker(String name);
	public List<WorkOrder> findByService(String name);
	public List<WorkOrder> findByWorkerIgnoreCaseContaining(String name);
	public List<WorkOrder> findByServiceIgnoreCaseContaining(String name);
	
	
	
}
