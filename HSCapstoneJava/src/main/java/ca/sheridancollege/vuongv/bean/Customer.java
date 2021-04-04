package ca.sheridancollege.vuongv.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String homePhone;
	private String cellPhone;
	private String address;
	private String city;
	private String postal;
	private String province;
	@OneToMany (cascade = CascadeType.ALL)
	private List<WorkOrder> workOrders;
	//private List<WorkOrder> workOrders = new ArrayList<WorkOrder>();
}
