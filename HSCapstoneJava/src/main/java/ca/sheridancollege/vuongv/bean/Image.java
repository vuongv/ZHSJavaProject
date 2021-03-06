package ca.sheridancollege.vuongv.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String imageName;
	@NonNull
	private String fullFilePath;
	@Builder.Default
	private Boolean toDisplay = true;

}
