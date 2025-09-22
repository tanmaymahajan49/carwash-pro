//package com.user;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.user.model.User;
//import com.user.repo.UserRepo;
//import com.user.security.GatewayOriginFilter;
//import com.user.service.JwtService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private UserRepo userRepo;
//	
//	@Autowired
//	private JwtService jwtService;
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Autowired
//	private GatewayOriginFilter gatewayOriginFilter;
//
////	@Bean
////	public JwtAuthFilter jwtAuthFilter() {
////		return new JwtAuthFilter();
////	}
//	
//	@Bean
//	public JwtAuthFilter jwtAuthFilter() {
//	    JwtAuthFilter filter = new JwtAuthFilter();
//	    filter.setJwtService(jwtService);
//	    filter.setUserDetailsService(userDetailsService);
//	    return filter;
//	}
//
//	
//
////	@Bean
////	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////		return http.csrf(csrf -> csrf.disable())
////				.authorizeHttpRequests(
////						auth -> auth.requestMatchers("/user/farmer-register", "/user/dealer-register", "/user/login")
////								.permitAll().anyRequest().authenticated())
////				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////				.authenticationProvider(authenticationProvider())
////				.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class).build();
////	}
//	
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    return http
//	        .csrf(csrf -> csrf.disable())
//	        .authorizeHttpRequests(auth -> 
//	            auth
//	                .requestMatchers("/user/farmer-register", "/user/dealer-register", "/user/login").permitAll()
//	                .anyRequest().authenticated())
//	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//	        .authenticationProvider(authenticationProvider())
//	        .addFilterBefore(gatewayOriginFilter, JwtAuthFilter.class)
//	        .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
//	        .build();
//	}
//
//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService());
//		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//		return provider;
//	}
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return email -> {
//
//			User user = userRepo.findByEmail(email);
//			if (user == null) {
//				new UsernameNotFoundException("User not found");
//			}
//
//			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//					List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
//		};
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//
//		return config.getAuthenticationManager();
//	}
//
//}



package com.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.user.model.User;
import com.user.repo.UserRepo;
import com.user.security.CustomAuthenticationEntryPoint;
import com.user.security.GatewayOriginFilter;
import com.user.service.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GatewayOriginFilter gatewayOriginFilter;
    
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepo.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
        };
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        // Inject JwtService and UserDetailsService into JwtAuthFilter constructor
        return new JwtAuthFilter(jwtService, userDetailsService());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/customer-register", "/user/washer-register", "/user/login").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint) 
                        )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                // Add your filters BEFORE UsernamePasswordAuthenticationFilter
                .addFilterBefore(gatewayOriginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
