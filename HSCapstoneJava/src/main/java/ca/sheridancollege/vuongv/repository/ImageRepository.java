package ca.sheridancollege.vuongv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.Image;
import ca.sheridancollege.vuongv.bean.Testimonial;

public interface ImageRepository extends JpaRepository<Image, Long> {

	public List<Image> findByToDisplay(Boolean b);

	public boolean existsByImageName(String cleanPath);

}
