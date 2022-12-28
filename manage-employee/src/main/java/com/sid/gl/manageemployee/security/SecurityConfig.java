package com.sid.gl.manageemployee.security;

import com.sid.gl.manageemployee.service.IEmployee;
import com.sid.gl.manageemployee.service.impl.EmployeeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EmployeeImpl userService;

    private final JwtTokenFilter jwtRequestFilter;

    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/public/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/*").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .anyRequest().authenticated().and().
                exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(exceptionHandlerFilter, JwtTokenFilter.class);

    }
}
