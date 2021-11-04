package ca.sheridancollege.vuongv.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Testimonial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testimonialId;
	@NonNull
	private String serviceName;
	@NonNull
	private String userEmail;
	@NonNull
	private String userName;
	@NonNull
	private String userTestimonial;
	@Builder.Default
	private Boolean toDisplay = false;
}
