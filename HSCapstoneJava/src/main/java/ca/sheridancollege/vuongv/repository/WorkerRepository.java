package ca.sheridancollege.vuongv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.WorkWorker;

public interface WorkerRepository extends JpaRepository<WorkWorker, Long> {
	public WorkWorker findByName (String name);
	public WorkWorker findByOrderList_WorkOrderId (Long id);
}
