package ca.sheridancollege.vuongv;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ca.sheridancollege.vuongv.bean.Customer;
import ca.sheridancollege.vuongv.bean.WorkOrder;
import ca.sheridancollege.vuongv.bean.WorkService;
import ca.sheridancollege.vuongv.controller.HSController;
import ca.sheridancollege.vuongv.repository.CustomerRepository;

@SpringBootTest
class CapstoneApplicationTests {

	@Test
	public void testCustomerCreationRegular() {
		boolean result;
		Customer customer = Customer
				.builder()
				.name("JSK")
				.email("a@a.com")
				.homePhone("1234567890")
				.cellPhone("1234567890")
				.address("ABC boulevard")
				.city("Brampton")
				.postal("L1S 2SA")
				.province("ON")
				.workOrders(new ArrayList<WorkOrder>())
				.build();
		if ( customer != null) {
			result = true;
		}else {
			result = false;
		}
		assertTrue(result,"Invalid Customer Creation");	
	}
	
	@Test
	public void testWorkOrderRegular() {
		boolean result;
		WorkOrder workOrder = WorkOrder
				.builder()
				.orderDate(LocalDate.now())
				.appointmentDate(LocalDate.now())
				.appointmentTime(LocalTime.now())
				.orderCost(BigDecimal.valueOf(Long.valueOf("10000")))
				.build();
		if ( workOrder != null) {
			result = true;
		} else {
			result = false;
		}
		assertTrue(result,"Invalid Work Order Creation");	
	}
	
//	@Test
//	public void testServiceCreationRegular() {
//		boolean result;
//		WorkService service = WorkService
//				.builder()
//				.
//	}

// Examples of why we can't do certain tests
// -------------------------------------------------------------	
//		@Test
//		public void testIndexRegular() {
//			HSController controller = new HSController();
//			Model model new Model();
//			boolean result;
//			if (controller.index(new Model()).equals("index") ) {
//				
//			}
//			assertTrue()
//		}
		
//		@Test
//	    public void testFindByServiceNameRegular() {
//	        private ServiceRepository serviceRepo;
//	        WorkService serv = serviceRepo.findByServiceName(orderServiceType);
//	        assertTrue();
//	    }
//	@Test 
//	public void testGetOrderList(HSController controller) {
//		boolean result = controller.getServiceOrderList()
//		serv.getOrderList();
//	}
	
//	@Test
//	public void testAddCustomer(HSController hs) {
//		
//		Customer customer = Customer
//				.builder()
//				.name("JSK")
//				.email("a@a.com")
//				.homePhone("1234567890")
//				.cellPhone("1234567890")
//				.address("ABC boulevard")
//				.city("Brampton")
//				.postal("L1S 2SA")
//				.province("ON")
//				.workOrders(new ArrayList<WorkOrder>())
//				.build();
//		boolean result = hs.addCustomer(customer);
//		assertTrue(result,"Customer was not added");
//	}
	
}
