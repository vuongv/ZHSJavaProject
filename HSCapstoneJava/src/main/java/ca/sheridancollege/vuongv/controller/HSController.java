package ca.sheridancollege.vuongv.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.String;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ca.sheridancollege.vuongv.bean.Customer;
import ca.sheridancollege.vuongv.bean.WorkOrder;
import ca.sheridancollege.vuongv.bean.WorkService;
import ca.sheridancollege.vuongv.bean.WorkWorker;
import ca.sheridancollege.vuongv.repository.CustomerRepository;
import ca.sheridancollege.vuongv.repository.OrderRepository;
import ca.sheridancollege.vuongv.repository.ServiceRepository;
import ca.sheridancollege.vuongv.repository.WorkerRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor

public class HSController {
	private OrderRepository orderRepo;
	private ServiceRepository serviceRepo;
	private CustomerRepository customerRepo;
	private WorkerRepository workerRepo;

	
	@GetMapping("/")
	public String index(Model model) { 
		return "index";
	}
	
	@GetMapping("/login") 
	public String login() {
		return "login"; 
	}
	
	@PostMapping("/login")
	public String adminView() {
		return "secure/adminView";
	}
	
	@GetMapping("/permission-denied") 
	public String permissionDenied() {
		return "error/permission-denied"; 
	}
	
	@GetMapping("/adminView")
	public String adminView_GET(Model model) {
		return "secure/adminView";
	}
	
	//ORDER
	@GetMapping("/adminView/addOrder")
	public String addOrder_GET(Model model) {
		List<WorkWorker> workerList = workerRepo.findAll();
		List<WorkService> serviceList = serviceRepo.findAll();
		
		model.addAttribute("workerList", workerList);
		model.addAttribute("serviceList", serviceList);
		return "secure/addOrder";
	}
	
	@PostMapping("/adminView/addOrder")
	public String addOrder_POST(Model model,

			@RequestParam String customerName,
			@RequestParam String customerEmail,
			@RequestParam String customerHomePhone,
			@RequestParam String customerCellPhone,
			@RequestParam String customerAddress,
			@RequestParam String customerCity,
			@RequestParam String customerPostal,
			@RequestParam String customerProvince,
			@RequestParam String orderServiceType,
			@RequestParam String orderWorker,
			@RequestParam String orderTotal,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate orderAppointmentDate,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime orderAppointmentTime
			
			) {
			
			//Assuming we are always creating a new customer
			//TODO: Take care of existing customers by checking if exist, if not, create new customer
			Customer cust = Customer
					.builder()
					.name(customerName)
					.email(customerEmail)
					.homePhone(customerHomePhone)
					.cellPhone(customerCellPhone)
					.address(customerAddress)
					.city(customerCity)
					.postal(customerPostal)
					.province(customerProvince)
					.workOrders(new ArrayList<WorkOrder>())
					.build();
			WorkOrder workOrder = WorkOrder
					.builder()
					.orderDate(LocalDate.now())
					.appointmentDate(orderAppointmentDate)
					.appointmentTime(orderAppointmentTime)
					.orderCost(BigDecimal.valueOf(Long.valueOf(orderTotal)))
					.worker(orderWorker)
					.service(orderServiceType)
					.build();
			
			cust.getWorkOrders().add(workOrder);
			orderRepo.save(workOrder); //line 85
			customerRepo.save(cust);
		return "redirect:/adminView/viewOrder";
	}
	// create a seperate saveWork method with parameters line 85
	@GetMapping("/adminView/viewOrder")
	public String viewOrder(Model model) {
		List<WorkOrder> orderList = orderRepo.findAll();
		List<WorkService> serviceList = new ArrayList<WorkService>();
		List<WorkWorker> workerList = new ArrayList<WorkWorker>();		
		List<Customer> customerList = new ArrayList<Customer>();
		for (WorkOrder order : orderList) {
			customerList.add(customerRepo.findByWorkOrders_WorkOrderId(order.getWorkOrderId()));
		}
//		for (WorkOrder order : orderList) {
//			serviceList.add(serviceRepo.findByOrderList_WorkOrderId(order.getWorkOrderId()));
//		}
//		for (WorkOrder order : orderList) {
//			workerList.add(workerRepo.findByOrderList_WorkOrderId(order.getWorkOrderId()));
//		}
		model.addAttribute("orderList", orderList);
		model.addAttribute("customerList", customerList);
		model.addAttribute("workerList", workerList);
		model.addAttribute("serviceList", serviceList);
		return "secure/viewOrder";
	}
	@PostMapping("/adminView/viewOrder")
	public String filterOrder(Model model, @RequestParam String searchInput, @RequestParam String filterOption) {
		System.out.println("this is "+searchInput);
		List<WorkOrder> orderList = new ArrayList<WorkOrder>();
		List<WorkService> serviceList = new ArrayList<WorkService>();
		List<WorkWorker> workerList = new ArrayList<WorkWorker>();		
		List<Customer> customerList = new ArrayList<Customer>();
		switch(filterOption) {
		case "1":
			orderList = orderRepo.findByServiceIgnoreCaseContaining(searchInput);
			break;
		case "2":
			orderList = orderRepo.findByWorkerIgnoreCaseContaining(searchInput);
			break;
		case "3":
			customerList = customerRepo.findByNameIgnoreCaseContaining(searchInput);
			for (Customer c : customerList) {
				for(WorkOrder w : c.getWorkOrders()) {
					orderList.add(orderRepo.findById(w.getWorkOrderId()).get());
				}
			}
			break;
		}
		
		if(!orderList.isEmpty()) {
			for (WorkOrder order : orderList) {
				customerList.add(customerRepo.findByWorkOrders_WorkOrderId(order.getWorkOrderId()));
			}
		}else {
			System.out.println("Order List is empty");
		}
		
//		for (WorkOrder order : orderList) {
//			serviceList.add(serviceRepo.findByOrderList_WorkOrderId(order.getWorkOrderId()));
//		}
//		for (WorkOrder order : orderList) {
//			workerList.add(workerRepo.findByOrderList_WorkOrderId(order.getWorkOrderId()));
//		}
		for (WorkOrder o : orderList) {
			System.out.println(o.getWorkOrderId());
		}
		model.addAttribute("orderList", orderList);
		model.addAttribute("customerList", customerList);
		model.addAttribute("workerList", workerList);
		model.addAttribute("serviceList", serviceList);
		return "secure/viewOrder";
	}
	
	@GetMapping("/adminView/deleteOrder/{workOrderId}")
	public String deleteOrder (Model model, @PathVariable String workOrderId, RedirectAttributes redirectAttributes) {
		RedirectView redirectView= new RedirectView("/viewOrder",true);
		Optional<WorkOrder> order = orderRepo.findById(Long.valueOf(workOrderId));
		
		Customer customer = customerRepo.findByWorkOrders_WorkOrderId(Long.valueOf(workOrderId));
		boolean customerResultDelete = customer.getWorkOrders().remove(order.get());
		
		
		orderRepo.deleteById(Long.valueOf(workOrderId));
		//Optional<WorkOrder> deletedOrder = orderRepo.findById(order.get().getWorkOrderId());
		if (orderRepo.findById(order.get().getWorkOrderId()).isEmpty()) {
			boolean deleteStatus = true;
			
			redirectAttributes.addFlashAttribute("deleteStatus", deleteStatus);
			//model.addAttribute("deleteStatus", deleteStatus);
			System.out.println("Order Deleted");
		}
		else {
			System.out.println("Order NOT Deleted");
		}
		redirectAttributes.addFlashAttribute("deletedOrder", order.get());
		//model.addAttribute("deletedOrder",order.get());
		
		
		return "redirect:/adminView/viewOrder";
	}
	
	
	@GetMapping("/adminView/editOrder/{customerId}/{workOrderId}")
	public String editOrder(Model model, @PathVariable Long workOrderId, 
			@PathVariable String customerId) {
		Optional<WorkOrder> order = orderRepo.findById(Long.valueOf(workOrderId));
		Optional<Customer> cust = customerRepo.findById(Long.valueOf(customerId));
		List<WorkWorker> workerList = workerRepo.findAll();
		List<WorkService> serviceList = serviceRepo.findAll();
		
		model.addAttribute("order", order.get());
		model.addAttribute("cust", cust.get());
		model.addAttribute("workerList", workerList);
		model.addAttribute("serviceList", serviceList);
		
		return "secure/editOrder";
	}
	
	@PostMapping("/adminView/editOrder/{customerId}/{workOrderId}")
	public String editOrder(Model model, @PathVariable Long workOrderId, 
			@PathVariable String customerId,
			@RequestParam String customerName,
			@RequestParam String customerEmail,
			@RequestParam String customerHomePhone,
			@RequestParam String customerCellPhone,
			@RequestParam String customerAddress,
			@RequestParam String customerCity,
			@RequestParam String customerPostal,
			@RequestParam String customerProvince,
			@RequestParam String orderServiceType,
			@RequestParam String orderWorker,
			@RequestParam String orderTotal,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate orderAppointmentDate,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime orderAppointmentTime) {
		Optional<WorkOrder> oldOrder = orderRepo.findById(Long.valueOf(workOrderId));
		Optional<Customer> oldCust = customerRepo.findById(Long.valueOf(customerId));
		WorkOrder order = WorkOrder.builder().workOrderId(workOrderId)
				.appointmentDate(orderAppointmentDate)
				.appointmentTime(orderAppointmentTime)
				.orderCost(new BigDecimal(orderTotal))
				.worker(orderWorker)
				.service(orderServiceType).build();
		Customer cust = Customer.builder().id(Long.valueOf(customerId))
				.name(customerName)
				.email(customerEmail)
				.homePhone(customerHomePhone)
				.cellPhone(customerCellPhone)
				.address(customerAddress)
				.city(customerCity)
				.postal(customerPostal)
				.province(customerProvince)
				.workOrders(oldCust.get().getWorkOrders())
				.build();
		
		customerRepo.save(cust);
		orderRepo.save(order);
		
		return "redirect:/adminView/viewOrder";
	}
	
	
	
	//CUSTOMER
	@GetMapping("/adminView/editCustomer/{customerId}")
	public String editCustomer(Model model, @PathVariable String customerId) {
		Optional<Customer> cust = customerRepo.findById(Long.valueOf(customerId));
		
		model.addAttribute("cust", cust.get());
		
		return "secure/editCustomer";
	}
	
	@PostMapping("/adminView/editCustomer")
	public String editCustomer(Model model, @RequestParam String id,
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String homePhone,
			@RequestParam String cellPhone,
			@RequestParam String address,
			@RequestParam String city,
			@RequestParam String postal,
			@RequestParam String province) {
		Optional<Customer> oldCust = customerRepo.findById(Long.valueOf(id));
		Customer cust = Customer.builder().id(Long.valueOf(id)).name(name).email(email)
				.homePhone(homePhone).cellPhone(cellPhone).address(address).city(city)
				.postal(postal).province(province).workOrders(oldCust.get().getWorkOrders()).build();
		customerRepo.save(cust);
		
		return "redirect:/adminView/viewCustomer";
	}
	
	@GetMapping("/adminView/deleteCustomer/{customerId}")
	public String deleteCustomer(Model model, @PathVariable String customerId, RedirectAttributes redirectAttributes) {
		Optional<Customer> cust = customerRepo.findById(Long.valueOf(customerId));
		boolean customerResultDelete;
		redirectAttributes.addFlashAttribute("cust", cust.get());

		
		if (cust.get().getWorkOrders().isEmpty()) {
			customerRepo.deleteById(Long.valueOf(customerId));
			customerResultDelete = true;
		}
		else {
			System.out.println("Cant delete");
			customerResultDelete = false;

		}
		
		redirectAttributes.addFlashAttribute("customerResultDelete", customerResultDelete);
		
		
		return "redirect:/adminView/viewCustomer";
		}
	
	@GetMapping("/adminView/viewCustomer")
	public String viewCustomer(Model model) {
		List<Customer> customerList = customerRepo.findAll();
		
		model.addAttribute("customerList", customerList);
		
		return "secure/viewCustomer";
	}
	@PostMapping("/adminView/viewCustomer")
	public String filterCustomer(Model model, @RequestParam String searchInput, @RequestParam String filterOption) {
		List<Customer> customerList = new ArrayList<Customer>();
		
		switch(filterOption) {
		case "1":
			customerList = customerRepo.findByNameIgnoreCaseContaining(searchInput);
			break;
		case "2":
			customerList = customerRepo.findByEmailIgnoreCaseContaining(searchInput);
			break;
		case "3":
			customerList = customerRepo.findByHomePhoneIgnoreCaseContaining(searchInput);
			break;
		case "4":
			customerList = customerRepo.findByAddressIgnoreCaseContaining(searchInput);
			break;
		case "5":
			customerList = customerRepo.findByCityIgnoreCaseContaining(searchInput);
			break;
		case "6":
			customerList = customerRepo.findByPostalIgnoreCaseContaining(searchInput);
			break;
		
		}
		model.addAttribute("customerList", customerList);

		return "secure/viewCustomer";
	}
	
	@GetMapping("/adminView/addCustomer")
	public String addCustomer(Model model){
		
		return "secure/addCustomer";
	}
	
	@PostMapping("/adminView/addCustomer")
	public String addCustomer(Model model, 
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String homePhone,
			@RequestParam String cellPhone,
			@RequestParam String address,
			@RequestParam String city,
			@RequestParam String postal,
			@RequestParam String province,
			RedirectAttributes redirectAttributes) {
		Boolean addSuccess;
		
		Customer cust = Customer
				.builder()
				.name(name)
				.email(email)
				.homePhone(homePhone)
				.cellPhone(cellPhone)
				.address(address)
				.city(city)
				.postal(postal)
				.province(province)
				.workOrders(new ArrayList<WorkOrder>())
				.build();
		
		Customer addedCust = customerRepo.save(cust);
		
		if (addedCust != null) {
			addSuccess = true;
		}
		else {
			addSuccess = false;
		}
		redirectAttributes.addFlashAttribute("addSuccess", addSuccess);
		
		return "redirect:/adminView/viewCustomer";
	}
	
	//SERVICE
	
	@GetMapping("/adminView/addService")
	public String addService_GET(Model model) {
		return "secure/addService";
	}
	
	@PostMapping("/adminView/addService")
	public String addService_POST(Model model,
			@RequestParam String serviceName,
			@RequestParam double serviceCost,
			@RequestParam String serviceDescription,
			@RequestParam int serviceDuration) {
		
		if(serviceRepo.existsByServiceName(serviceName)) {
			
			model.addAttribute("duplicate", "Sorry, this Service already exists, please enter a new service");
			return "addService";
	    }
		
		WorkService service = WorkService
				.builder()
				.serviceName(serviceName)
				.serviceCost(serviceCost)
				.serviceDescription(serviceDescription)
				.serviceDuration(serviceDuration)
				.build();
		serviceRepo.save(service);
		model.addAttribute("service", service);
		return "redirect:/adminView/viewService";
	}
	
	@GetMapping("/adminView/viewService")
	public String viewService(Model model) {
		List<WorkService> serviceList = serviceRepo.findAll();
		model.addAttribute("serviceList", serviceList);
		return "secure/viewService";
	}
	
	@PostMapping("/adminView/viewService")
	public String filterService(Model model, @RequestParam String searchInput) {
		List<WorkService> serviceList = serviceRepo.findByServiceNameIgnoreCaseContaining(searchInput);
		model.addAttribute("serviceList", serviceList);
		return "secure/viewService";
	}
	@GetMapping("/adminView/deleteService/{serviceId}")
	public String deleteService (Model model, @PathVariable String serviceId, RedirectAttributes redirectAttributes ) {
		RedirectView redirectView = new RedirectView("/viewService",true);
		serviceRepo.deleteById(Long.valueOf(serviceId));
		
		redirectAttributes.addFlashAttribute("deleteService");
		return "redirect:/adminView/viewService";
	}
	
	@GetMapping("/adminView/editService/{serviceId}")
	public String editService(Model model, @PathVariable String serviceId) {
		Optional<WorkService> serv = serviceRepo.findById(Long.valueOf(serviceId));
		model.addAttribute("serv", serv.get());
		return "secure/editService";
	}
	
	@PostMapping("/adminView/editService")
	public String editService(Model model,  
		@RequestParam String serviceId,
		@RequestParam String serviceName,
		@RequestParam double serviceCost,
		@RequestParam String serviceDescription, 
		@RequestParam int serviceDuration ) {
		
		Optional<WorkService> serv = serviceRepo.findById(Long.valueOf(serviceId));
		
		List<WorkOrder> relatedWorkOrder = orderRepo.findByService(serv.get().getServiceName()) ;

		WorkService service = WorkService.builder()
				.serviceId(Long.valueOf(serviceId))
				.serviceName(serviceName)
				.serviceCost(serviceCost)
				.serviceDuration(serviceDuration)
				.serviceDescription(serviceDescription)
				.build();

		for(WorkOrder w : relatedWorkOrder) {
			w.setService(serviceName);
			orderRepo.save(w);
		}
		
		serviceRepo.save(service);
		return "redirect:/adminView/viewService";
	}

	//WORKER
	
	@GetMapping("/adminView/viewWorker")
	public String viewWorker(Model model) {
		List<WorkWorker> workerList = workerRepo.findAll();
		model.addAttribute("workerList", workerList);
		return "secure/viewWorker";
	}
	@PostMapping("/adminView/viewWorker")
	public String filterWorker(Model model, @RequestParam String searchInput) {
		List<WorkWorker> workerList = workerRepo.findByNameIgnoreCaseContaining(searchInput);
		model.addAttribute("workerList", workerList);
		return "secure/viewWorker";
	}

	
	@GetMapping("/adminView/addWorker")
	public String addWorker(Model model) {

		return "secure/addWorker";
	}
	
	@PostMapping("/adminView/addWorker")
	public String addWorker_POST(Model model,
			@RequestParam String name) {
		
		if(workerRepo.existsByName(name)) {
			
			model.addAttribute("duplicate", "Sorry, this worker already exists, please enter a new user");
			return "addWorker";
	    }
		
		WorkWorker worker = WorkWorker
				.builder()
					.name(name)
					.build();
		workerRepo.save(worker);
		model.addAttribute("worker", worker);
		List<WorkWorker> workerList = workerRepo.findAll();
		model.addAttribute("workerList", workerList);
		
		return "secure/viewWorker";
	}

	@GetMapping("/adminView/deleteWorker/{workerId}")
	public String deleteWorker(Model model, @PathVariable String workerId, RedirectAttributes redirectAttributes) {
		
		RedirectView redirectView = new RedirectView("/viewWorker", true);
		
		workerRepo.deleteById(Long.valueOf(workerId));

		redirectAttributes.addFlashAttribute("deleteWorker");
		
		return "redirect:/adminView/viewWorker";
	}
	
	@GetMapping("/adminView/editWorker/{workerId}")
	public String editWorker(Model model, @PathVariable String workerId) {
		
		Optional<WorkWorker> worker = workerRepo.findById(Long.valueOf(workerId));
		
		model.addAttribute("worker", worker.get());
		
		return "secure/editWorker";
	}
	
	

	@PostMapping("/adminView/editWorker")
	public String editCustomer(Model model, @RequestParam String id, @RequestParam String name) {
		
		Optional<WorkWorker> oldWorker = workerRepo.findById(Long.valueOf(id));
		List<WorkOrder> relatedWorkOrder = orderRepo.findByWorker(oldWorker.get().getName()) ;
		
		WorkWorker worker = WorkWorker.builder().id(Long.valueOf(id)).name(name).build();
		
		for(WorkOrder w : relatedWorkOrder) {
			w.setWorker(name);
			orderRepo.save(w);
		}
		
		workerRepo.save(worker);
		
		return "redirect:/adminView/viewWorker";
	}
}
