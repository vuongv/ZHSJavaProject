package ca.sheridancollege.vuongv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.WorkOrder;

public interface OrderRepository extends JpaRepository<WorkOrder, Long> {
	
}
