package ca.sheridancollege.vuongv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public Customer findByWorkOrders_WorkOrderId (Long id);
	public List<Customer> findByNameContaining (String name);

}
