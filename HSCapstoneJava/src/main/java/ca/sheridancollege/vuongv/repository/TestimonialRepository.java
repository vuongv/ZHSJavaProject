package ca.sheridancollege.vuongv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
	public List<Testimonial> findByToDisplay(Boolean b);

	public List<Testimonial> findByServiceNameIgnoreCaseContaining(String searchInput);
	public List<Testimonial> findByUserNameIgnoreCaseContaining(String searchInput);
	public List<Testimonial> findByUserEmailIgnoreCaseContaining(String searchInput);
}
