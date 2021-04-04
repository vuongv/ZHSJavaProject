package ca.sheridancollege.vuongv.bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class WorkOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workOrderId;
//	@OneToOne (cascade=CascadeType.REMOVE, optional=true)
//	@JoinTable (name="WORK_ORDER_SERVICE", joinColumns = @JoinColumn(name="WORK_ORDER_ID"), inverseJoinColumns=@JoinColumn(name="SERVICE_ID"))
//	private WorkService service;
	private LocalDate orderDate;
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	private BigDecimal orderCost;
//	@OneToOne (cascade=CascadeType.REMOVE, optional=true)
//	@JoinTable (name="WORK_ORDER_WORKER", joinColumns = @JoinColumn(name="WORK_ORDER_ID"), inverseJoinColumns=@JoinColumn(name="WORKER_ID"))
//	private WorkWorker worker;
	
}
