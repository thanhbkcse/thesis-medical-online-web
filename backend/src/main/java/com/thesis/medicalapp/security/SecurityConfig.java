package com.thesis.medicalapp.security;

import com.thesis.medicalapp.filter.CustomAuthenticationFilter;
import com.thesis.medicalapp.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebSocket
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure");
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll()
                .mvcMatchers("/ws/**").permitAll()
                .antMatchers(GET, "/ws/**").permitAll()
                .antMatchers(POST, "/ws/**").permitAll()
                .antMatchers(POST, "/api/auth/register/**").permitAll()
                .antMatchers(GET, "/api/users/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(GET, "/api/payment/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(PATCH, "/api/users/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/profiles/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/schedules/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/upload/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/files/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/appointments/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/doctor/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(GET, "/api/doctor/schedules/date").hasAnyAuthority( "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/records/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/doctors").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST, "/api/rooms/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST,"/api/hospitals").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(POST,"/api/favorites/**").hasAnyAuthority("ROLE_USER", "ROLE_DOCTOR", "ROLE_ADMIN")
                .antMatchers(GET, "/api/specialties/**").permitAll()
                .antMatchers(GET, "/api/schedules/**").permitAll()
                .antMatchers(GET, "/api/doctors/**").permitAll()
                .antMatchers(GET,"/api/hospitals/**").permitAll()
                .antMatchers(POST, "/api/hospitals/search").permitAll()
                .antMatchers(POST, "/api/doctors/search").permitAll()
                .antMatchers(POST, "/api/services/search").permitAll()
                .antMatchers(GET, "/api/services/hospital").permitAll()
                .antMatchers(GET,"/api/services").permitAll()
                .antMatchers(GET,"/api/store-medicines/**").permitAll()
                .antMatchers(POST, "/api/qa/**").permitAll()
                .antMatchers(POST, "/api/payment/momo/verify").permitAll()
                .antMatchers(GET, "/api/questions/**").permitAll()
                .antMatchers(GET, "/api/favorites/**").permitAll()
                .antMatchers(POST, "/api/generic-search").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout( logout -> logout.logoutSuccessUrl("/"));
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("1234")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
