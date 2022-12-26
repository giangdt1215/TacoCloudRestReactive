package com.dtg.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.dtg.tacocloud.data.jpa.UserRepository;
import com.dtg.tacocloud.model.User;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(5);
	}
	
//	@Bean
//	public UserDetailsService userDetailService(PasswordEncoder encoder) {
//		List<UserDetails> usersList = new ArrayList<>();
//		usersList.add(new User("buzz", encoder.encode("password"),
//				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//		usersList.add(new User("woody", encoder.encode("password"),
//				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//		
//		return new InMemoryUserDetailsManager(usersList);
//	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			if(user != null)
				return user;
			
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests()
					.antMatchers("/design", "/orders").hasRole("USER")
					.antMatchers("/", "/**").permitAll()
					.antMatchers("/api/**").permitAll()
					//config for h2-console page and error
					.antMatchers("/h2-console/**", "/console/**").permitAll()
					.antMatchers("/error/**").permitAll()
				.and()
					.formLogin()
						.loginPage("/login")
				.and()
					.logout()
						.logoutSuccessUrl("/")
				.and()
					.csrf().ignoringAntMatchers("/api/**", "/data-api/**",
						"/h2-console/**", "/console/**", "/error/**") // for access h2-console
				.and()
				//for access h2-console
				.headers().frameOptions().disable()
				.and()
				.build();
	}
}
