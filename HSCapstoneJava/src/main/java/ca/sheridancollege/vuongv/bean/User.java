package ca.sheridancollege.vuongv.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String username;
	@NonNull
	private String encryptedPassword;
	@NonNull
	private Boolean enabled;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();
}
