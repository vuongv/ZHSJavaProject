package ca.sheridancollege.vuongv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.WorkWorker;

public interface WorkerRepository extends JpaRepository<WorkWorker, Long> {
	
	public WorkWorker findByName (String name);
	
	public Boolean existsByName(String name);
	
	public List<WorkWorker> findByNameIgnoreCaseContaining (String name);
	
	public List<WorkWorker> findByOrderByName ();
	
//	public List<WorkWorker> findByNameOrderByNameContaining(String name);
}
