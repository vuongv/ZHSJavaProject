package ca.sheridancollege.vuongv.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import lombok.NoArgsConstructor;

@Controller
@AllArgsConstructor
public class HSController {
	private OrderRepository orderRepo;
	private ServiceRepository serviceRepo;
	private CustomerRepository customerRepo;
	private WorkerRepository workerRepo;
	
	
	//Cannot create Junit test since we are unable to instantiate type Model. Spring boot handles this.
	@GetMapping("/")
	public String index(Model model) { 
		return "index";
	}
	
	@GetMapping("/adminView")
	public String adminView_GET(Model model) {
		return "adminView";
	}

	@GetMapping("/addOrder")
	public String addOrder_GET(Model model) {
		List<WorkWorker> workerList = workerRepo.findAll();
		List<WorkService> serviceList = serviceRepo.findAll();
		
		model.addAttribute("workerList", workerList);
		model.addAttribute("serviceList", serviceList);
		return "addOrder";
	}
	
	@PostMapping("/addOrder")
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
		return "adminView";
	}
	// create a seperate saveWork method with parameters line 85
	@GetMapping("/viewOrder")
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
		return "viewOrder";
	}
	
	@GetMapping("/deleteOrder/{workOrderId}")
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
		
		
		return "redirect:/viewOrder";
	}
	
	@GetMapping("/deleteCustomer/{customerId}")
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
		
		
		return "redirect:/viewCustomer";
	}
	
	@GetMapping("/editOrder/{customerId}/{workOrderId}")
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
		
		return "editOrder";
	}
	
	@PostMapping("/editOrder/{customerId}/{workOrderId}")
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
		
		return "redirect:/viewOrder";
	}
	
	@GetMapping("/editCustomer/{customerId}")
	public String editCustomer(Model model, @PathVariable String customerId) {
		Optional<Customer> cust = customerRepo.findById(Long.valueOf(customerId));
		
		model.addAttribute("cust", cust.get());
		
		return "editCustomer";
	}
	
	@PostMapping("/editCustomer")
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
		
		return "redirect:/viewCustomer";
	}
	
	@GetMapping("/viewCustomer")
	public String viewCustomer(Model model) {
		List<Customer> customerList = customerRepo.findAll();
		
		model.addAttribute("customerList", customerList);
		
		return "viewCustomer";
	}
	@GetMapping("/addCustomer")
	public String addCustomer(Model model){
		
		return "addCustomer";
	}
	
	@PostMapping("/addCustomer")
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
		
		return "redirect:/viewCustomer";
	}
	
	@GetMapping("/addService")
	public String addService_GET(Model model) {
		return "addService";
	}
	@PostMapping("/addService")
	public String addService_POST(Model model,
			@RequestParam String serviceName,
			@RequestParam double serviceCost,
			@RequestParam String serviceDescription,
			@RequestParam int serviceDuration) {
		
		WorkService service = WorkService
				.builder()
				.serviceName(serviceName)
				.serviceCost(serviceCost)
				.serviceDescription(serviceDescription)
				.serviceDuration(serviceDuration)
				.build();
		serviceRepo.save(service);
		model.addAttribute("service", service);
		return "viewService";
	}
	@GetMapping("/viewService")
	public String viewService(Model model) {
		List<WorkService> serviceList = serviceRepo.findAll();
		model.addAttribute("serviceList", serviceList);
		return "viewService";
	}
	@GetMapping("/deleteService/{serviceId}")
	public String deleteService (Model model, @PathVariable String serviceId, RedirectAttributes redirectAttributes ) {
		RedirectView redirectView = new RedirectView("/viewService",true);
		serviceRepo.deleteById(Long.valueOf(serviceId));
		
		redirectAttributes.addFlashAttribute("deleteService");
		return "redirect:/deleteService";
	}

	@GetMapping("/viewWorker")
	public String viewWorker(Model model) {
		List<WorkWorker> workerList = workerRepo.findAll();
		model.addAttribute("workerList", workerList);
		return "viewWorker";
	}

	@GetMapping("/addWorker")
	public String addWorker(Model model) {

		return "addWorker";
	}

	@GetMapping("/deleteWorker/{workerId}")
	public String deleteWorker(Model model, @PathVariable String workerId, RedirectAttributes redirectAttributes) {

		Optional<WorkWorker> worker = workerRepo.findById(Long.valueOf(workerId));
		boolean customerResultDelete;
		redirectAttributes.addFlashAttribute("worker", worker.get());

		/*
		 * if (worker.get().getWorkOrders().isEmpty()) {
		 * customerRepo.deleteById(Long.valueOf(customerId)); customerResultDelete =
		 * true; } else { System.out.println("Cant delete"); customerResultDelete =
		 * false;
		 * 
		 * }
		 * 
		 * redirectAttributes.addFlashAttribute("customerResultDelete",
		 * customerResultDelete);
		 */
		return "redirect:/viewWorker";
	}

	
	@GetMapping("/editWorker/{workerId}")
	public String editWorker(Model model, @PathVariable String workerId) {
		Optional<WorkWorker> worker = workerRepo.findById(Long.valueOf(workerId));
		
		model.addAttribute("worker", worker.get());
		
		return "editWorker";
	}

	@PostMapping("/editWorker")
	public String editCustomer(Model model, @RequestParam String id, @RequestParam String name) {
		
		Optional<WorkWorker> oldWorker = workerRepo.findById(Long.valueOf(id));
		WorkWorker worker = WorkWorker.builder().id(Long.valueOf(id)).name(name).build();
		workerRepo.save(worker);
		
		return "redirect:/viewWorker";
	}
}
