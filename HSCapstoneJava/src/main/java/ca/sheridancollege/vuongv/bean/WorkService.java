package ca.sheridancollege.vuongv.bean;

import java.util.ArrayList;
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
public class WorkService {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long serviceId;
	private String serviceName;
	private double serviceCost;
	private String serviceDescription;
	private int serviceDuration;
	
}
