package ca.sheridancollege.vuongv;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ca.sheridancollege.vuongv.bean.Customer;
import ca.sheridancollege.vuongv.bean.WorkOrder;
import ca.sheridancollege.vuongv.bean.WorkService;
import ca.sheridancollege.vuongv.bean.WorkWorker;
import ca.sheridancollege.vuongv.controller.HSController;
import ca.sheridancollege.vuongv.repository.CustomerRepository;
import ca.sheridancollege.vuongv.repository.OrderRepository;
import ca.sheridancollege.vuongv.repository.ServiceRepository;
import ca.sheridancollege.vuongv.repository.WorkerRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CapstoneApplicationTests {
	
	//Create a Mock MVC as a foundation
	@Autowired
	private MockMvc mockMvc;
	
	//Create a Mock Customer Repo for Controller to use
	@MockBean
	private CustomerRepository customerRepo;
	
	//Create a Mock Order REpo for Controller to use
	@MockBean
	private OrderRepository orderRepo;
	
	//Create a Mock Service repo for Controller to use
	@MockBean
	private ServiceRepository serviceRepo;
	
	//Create a Mock Worker repo for Controller to use
	@MockBean
	private WorkerRepository workerRepo;

	//Success test to view Customer Page
	@Test
	public void testViewCustomer() throws Exception{
		this.mockMvc.perform(get("/viewCustomer")).andExpect(status().isOk()).andExpect(view().name("viewCustomer"));
	}
	
	//Success test to View Order Page
	@Test
	public void testViewOrder() throws Exception{
		this.mockMvc.perform(get("/viewOrder")).andExpect(status().isOk()).andExpect(view().name("viewOrder"));
	}
	
	//Success test to View Service page
	@Test
	public void testViewService() throws Exception{
		this.mockMvc.perform(get("/viewService")).andExpect(status().isOk()).andExpect(view().name("viewService"));
	}
	
	//Success test to View Worker page
	@Test
	public void testViewWorker() throws Exception{
		this.mockMvc.perform(get("/viewWorker")).andExpect(status().isOk()).andExpect(view().name("viewWorker"));
	}
	
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
	
	//Success test to index
	@Test 
	public void testViewHome() throws Exception{
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("adminView"));
	} 
	

	
	//Success creating Work Order
	@Test
	public void testCreateWorkOrder() throws Exception {
		MultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("customerName", "Sebastian Kap3");
		requestParams.add("customerEmail", "kap3@gmail.com");
		requestParams.add("customerHomePhone", "(123) 456-7890");
		requestParams.add("customerCellPhone", "(123) 456-7890");
		requestParams.add("customerAddress", "123 play ground stret");
		requestParams.add("customerCity", "Los Santos");
		requestParams.add("customerPostal", "1Z2 3X4");
		requestParams.add("customerProvince", "ON");
		requestParams.add("orderServiceType","Chimney cleaning");
		requestParams.add("orderWorker", "Kap3");
		requestParams.add("orderTotal", "1234");
		requestParams.add("orderAppointmentDate", LocalDate.now().toString());
		requestParams.add("orderAppointmentTime", LocalTime.now().toString());
		
		this.mockMvc.perform(post("/addOrder").params(requestParams))
			.andExpect(redirectedUrl("/viewOrder"))
			.andExpect(status().isFound());	
		
	}
	
	//Success creating Service 
	@Test
	public void testCreateService() throws Exception {
		MultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("serviceName", "Building da table");
		requestParams.add("serviceCost", "20.00");
		requestParams.add("serviceDescription", "This is a table building order");
		requestParams.add("serviceDuration", "2");
		
		this.mockMvc.perform(post("/addService").params(requestParams))
				.andExpect(redirectedUrl("/viewService"))
				.andExpect(status().isFound());	
		}
	
	//Success Creating Customer
	@Test
	public void testCreateCustomer() throws Exception {
		MultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("name", "Khoa");
		requestParams.add("email", "123@gmail.com");
		requestParams.add("homePhone", "(123) 456-6789");
		requestParams.add("cellPhone", "(123) 456-6789");
		requestParams.add("address", "123 plae ground stret");
		requestParams.add("city", "Los Santos");
		requestParams.add("postal", "1z2 3z5");
		requestParams.add("province", "ON");
		
		this.mockMvc.perform(post("/addCustomer").params(requestParams))
		.andExpect(redirectedUrl("/viewCustomer"))
		.andExpect(status().isFound());	

	}
	
	//Success Creating Worker
	@Test
	public void testCreateWorker() throws Exception {
		MultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("name", "Brute");
		
		this.mockMvc.perform(post("/addWorker").params(requestParams))
		.andExpect(status().isOk())
		.andExpect(view().name("viewWorker"));
	}
	
	//Success Edit Worker
//	@Test
//	public void testEditWorker() throws Exception {
//		WorkWorker seb =  WorkWorker.builder().id(Long.valueOf("1")).name("Kap4").build();
//		entityManager.persist(seb);
//		entityManager.flush();
//		
//		MultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
//		requestParams.add("id", "1");
//		requestParams.add("name", "Kap4");
//		
//		this.mockMvc.perform(post("/editWorker").params(requestParams))
//		.andExpect(redirectedUrl("/viewWorker"))
//		.andExpect(status().isFound());
//	}
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
