package ca.sheridancollege.vuongv.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests()
			.antMatchers("/adminView/view*").hasAnyRole("EMPLOYEE")
			.antMatchers("/adminView").hasAnyRole("EMPLOYEE")
			.antMatchers("/adminView/**").hasRole("ADMIN")
			//.antMatchers("/adminView/**").hasRole("DEV")
			
			.antMatchers("/", "/js/**", "/css/**", "/images/**").permitAll() 
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/testimonials").permitAll()
			.antMatchers("/gallery").permitAll()
			.anyRequest().authenticated()
			.and().formLogin() 
				.loginPage("/login").permitAll()
			.and().logout() 
				.invalidateHttpSession(true)
				. clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
				.logoutSuccessUrl("/login").permitAll()
				.and()
					.exceptionHandling() 
					.accessDeniedHandler(accessDeniedHandler);
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication() 
//			.passwordEncoder(NoOpPasswordEncoder.getInstance())
//				.withUser("frank@frank.com").password("1234").roles("USER") 
//				.and() 
//				.withUser("guest@guest.com").password("abcd").roles("GUEST");
//	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
}
