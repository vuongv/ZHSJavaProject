package ca.sheridancollege.vuongv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public Customer findByWorkOrders_WorkOrderId (Long id);
	public List<Customer> findByNameContaining (String name);
	public List<Customer> findByNameIgnoreCaseContaining(String name);
	public List<Customer> findByEmailIgnoreCaseContaining(String email);
	public List<Customer> findByHomePhoneIgnoreCaseContaining(String homePhone);
	public List<Customer> findByAddressIgnoreCaseContaining(String address);
	public List<Customer> findByCityIgnoreCaseContaining(String city);
	public List<Customer> findByPostalIgnoreCaseContaining(String postal);
	

}
