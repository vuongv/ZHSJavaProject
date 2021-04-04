package ca.sheridancollege.vuongv.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ca.sheridancollege.vuongv.bean.Customer;
import ca.sheridancollege.vuongv.bean.WorkOrder;

@SpringBootTest
public class HSControllerTest {

	@Test
	public void testTest() {
		boolean result = true;
//		assertTrue("", result);
		fail("failed");
	}
	
//	@Test
//	public void testCustomerCreationRegular() {
//		boolean result;
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
//		if ( customer != null) {
//			result = true;
//		}else {
//			result = false;
//		}
//		assertTrue("Invalid Customer Creation", result);
//		
//	}
	
	

// Examples of why we can't do certain tests
// -------------------------------------------------------------	
//	@Test
//	public void testIndexRegular() {
//		HSController controller = new HSController();
//		Model model new Model();
//		boolean result;
//		if (controller.index(new Model()).equals("index") ) {
//			
//		}
//		assertTrue()
//	}
	
//	@Test
//    public void testFindByServiceNameRegular() {
//        private ServiceRepository serviceRepo;
//        WorkService serv = serviceRepo.findByServiceName(orderServiceType);
//        assertTrue();
//    }
	

}
